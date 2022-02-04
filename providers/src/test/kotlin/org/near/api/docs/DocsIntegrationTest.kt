package org.near.api.docs

import io.kotest.common.runBlocking
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import org.commonmark.node.*
import org.commonmark.parser.Parser
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.near.api.json.ObjectMapperFactory
import java.io.File
import kotlin.test.Ignore


/**
 * This test is used to verify response formats of e
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DocsIntegrationTest {

    private val githubReposPrefix = "https://api.github.com/repos"
    private val docsRepoPath = "near/docs"
    private val rpcPrefix = "docs/api/rpc/"
    private val githubContentPrefix = "https://raw.githubusercontent.com"
    private val objectMapper = ObjectMapperFactory.newInstance()

    private val client: HttpClient = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(JsonFeature) {
            serializer = JacksonSerializer(
                jackson = objectMapper
            )
        }
        install(HttpTimeout)
    }
    private val classMapping = HashMap<String, String>()

    @BeforeAll
    fun loadMappings() {
        val path = this.javaClass.getResource("/docs/.index")!!.path
        val file = File(path)

        classMapping.putAll(
            file.readLines()
                .map {
                    it.substringBefore("=") to it.substringAfter("=")
                }
        )
    }

    @Test
    @Ignore
    fun validateDocs() {
        runBlocking {
            val loadedExamples = loadExamples()

            for (example in loadedExamples) {
                val className = classMapping[example.code]
                if (className != null) {
                    val clazz = Class.forName(className)
                    val obj = objectMapper.readValue(example.response, clazz)
                    println(obj)
                } else {
                  // mapping required
                }
            }
        }
    }

    private suspend fun loadExamples(): List<Example> {
        val tree = client.get<GithubTree>("$githubReposPrefix/$docsRepoPath/git/trees/master?recursive=1") {}
        val allRpcDocs =
            tree.tree.filter { it.path.startsWith(rpcPrefix) }.associateBy { it.path.substring(rpcPrefix.length) }
        val visitor = ExampleResponsesVisitor()
        for (allRpcDoc in allRpcDocs) {
            loadAndParseDocFile(allRpcDoc.value.path, visitor)
        }
        return visitor.examples
    }

    private suspend fun loadAndParseDocFile(path: String, visitor: ExampleResponsesVisitor) {
        val content = client.get<String>("$githubContentPrefix/$docsRepoPath/master/$path") {}
        val parser: Parser = Parser.builder().build()
        val document: Node = parser.parse(content)
        document.accept(visitor)
    }
}

data class GithubTree(
    val sha: String,
    val url: String,
    val tree: List<GithubTreeElement>
)

data class GithubTreeElement(
    val path: String,
    val mode: String,
    val type: String,
    val size: Int,
    val sha: String,
    val url: String
)

data class Example(
    var code: String,
    var response: String
)

internal class ExampleResponsesVisitor : AbstractVisitor() {

    val examples = ArrayList<Example>()
    var currentExampleCode: String? = null

    override fun visit(heading: Heading) {
        // heading on level 3 is a RPC API method
        if (heading.level == 3 && heading.firstChild is Text) {
            val header = (heading.firstChild as Text).literal
            currentExampleCode = header.substringAfter("#").substringBefore("}").trimIndent()
        }

        visitChildren(heading)
    }

    override fun visit(htmlBlock: HtmlBlock) {
        if (currentExampleCode == null) {
            // just a normal html
        } else {
            if (htmlBlock.literal.contains("Example response: ")) {
                if (htmlBlock.next is FencedCodeBlock) {
                    val fencedCodeBlock = htmlBlock.next as FencedCodeBlock
                    if (fencedCodeBlock.info == "json") {
                        examples.add(Example(currentExampleCode!!, fencedCodeBlock.literal))
                        currentExampleCode = null
                    }

                }
            }
        }
        visitChildren(htmlBlock)
    }
}
