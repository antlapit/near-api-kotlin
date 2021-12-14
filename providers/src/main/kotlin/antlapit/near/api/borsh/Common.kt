package antlapit.near.api.borsh

import org.near.borshj.Borsh

/**
 * Workaround for enums
 * TODO should be in borshj
 */
data class BorshEnum(
    val index: Byte,
    val data: Any
) : Borsh

/**
 * Workaround for fixed array with 32 elements
 * TODO should be in borshj
 */
data class BorshArray32(
    val b0: Byte,
    val b1: Byte,
    val b2: Byte,
    val b3: Byte,
    val b4: Byte,
    val b5: Byte,
    val b6: Byte,
    val b7: Byte,
    val b8: Byte,
    val b9: Byte,
    val b10: Byte,
    val b11: Byte,
    val b12: Byte,
    val b13: Byte,
    val b14: Byte,
    val b15: Byte,
    val b16: Byte,
    val b17: Byte,
    val b18: Byte,
    val b19: Byte,
    val b20: Byte,
    val b21: Byte,
    val b22: Byte,
    val b23: Byte,
    val b24: Byte,
    val b25: Byte,
    val b26: Byte,
    val b27: Byte,
    val b28: Byte,
    val b29: Byte,
    val b30: Byte,
    val b31: Byte,
) : Borsh {
    constructor(b: ByteArray) : this(
        b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7],
        b[8], b[9], b[10], b[11], b[12], b[13], b[14], b[15],
        b[16], b[17], b[18], b[19], b[20], b[21], b[22], b[23],
        b[24], b[25], b[26], b[27], b[28], b[29], b[30], b[31]
    )
}

/**
 * Workaround for fixed array with 64 elements
 * TODO should be in borshj
 */
data class BorshArray64(
    val b0: Byte,
    val b1: Byte,
    val b2: Byte,
    val b3: Byte,
    val b4: Byte,
    val b5: Byte,
    val b6: Byte,
    val b7: Byte,
    val b8: Byte,
    val b9: Byte,
    val b10: Byte,
    val b11: Byte,
    val b12: Byte,
    val b13: Byte,
    val b14: Byte,
    val b15: Byte,
    val b16: Byte,
    val b17: Byte,
    val b18: Byte,
    val b19: Byte,
    val b20: Byte,
    val b21: Byte,
    val b22: Byte,
    val b23: Byte,
    val b24: Byte,
    val b25: Byte,
    val b26: Byte,
    val b27: Byte,
    val b28: Byte,
    val b29: Byte,
    val b30: Byte,
    val b31: Byte,
    val b32: Byte,
    val b33: Byte,
    val b34: Byte,
    val b35: Byte,
    val b36: Byte,
    val b37: Byte,
    val b38: Byte,
    val b39: Byte,
    val b40: Byte,
    val b41: Byte,
    val b42: Byte,
    val b43: Byte,
    val b44: Byte,
    val b45: Byte,
    val b46: Byte,
    val b47: Byte,
    val b48: Byte,
    val b49: Byte,
    val b50: Byte,
    val b51: Byte,
    val b52: Byte,
    val b53: Byte,
    val b54: Byte,
    val b55: Byte,
    val b56: Byte,
    val b57: Byte,
    val b58: Byte,
    val b59: Byte,
    val b60: Byte,
    val b61: Byte,
    val b62: Byte,
    val b63: Byte,
) : Borsh {
    constructor(b: ByteArray) : this(
        b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7],
        b[8], b[9], b[10], b[11], b[12], b[13], b[14], b[15],
        b[16], b[17], b[18], b[19], b[20], b[21], b[22], b[23],
        b[24], b[25], b[26], b[27], b[28], b[29], b[30], b[31],
        b[32], b[33], b[34], b[35], b[36], b[37], b[38], b[39],
        b[40], b[41], b[42], b[43], b[44], b[45], b[46], b[47],
        b[48], b[49], b[50], b[51], b[52], b[53], b[54], b[55],
        b[56], b[57], b[58], b[59], b[60], b[61], b[62], b[63],
    )
}
