package site.ani4h.api.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.primitives.Longs;
import hera.util.Base58Utils;

@lombok.Getter
@lombok.Setter
public class Uid {
    public Uid(int localId, int shardId, int typeId) {
        this.localId = localId;
        this.shardId = shardId;
        this.typeId = typeId;
    }
    @Override
    @JsonValue
    public String toString() {
        long uid = (long) localId << 28 | ((long) shardId << 18) | (long) typeId;
        return Base58Utils.encode(Longs.toByteArray(uid));
    }
    @JsonCreator
    public Uid (String str) {
        byte[] b;
        try {
            b = Base58Utils.decode(str);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid UID");
        }
        long uid = Longs.fromByteArray(b);
        if ((1 << 18) > uid) {
            throw new IllegalArgumentException("Invalid UID");
        }

        this.localId =  (int) uid >> 28;
        this.shardId = (int)(uid >> 18 & 0x3FF);
        this.typeId = (int) (uid & 0x3FFF);
    }
    private int localId;
    private int shardId;
    private int typeId;
}
