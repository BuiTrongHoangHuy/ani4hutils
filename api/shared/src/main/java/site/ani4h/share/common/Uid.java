package site.ani4h.share.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import hera.util.Base58Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@lombok.Getter
@lombok.Setter
public class Uid {
    private int localId;
    private int shardId;
    private int typeId;

    public Uid(int localId, int shardId, int typeId) {
        this.localId = localId;
        this.shardId = shardId;
        this.typeId = typeId;
    }

    @Override
    @JsonValue
    public String toString() {
        long uid = ((long) localId << 28L) | ((long) typeId << 18L) | (long) shardId;
        return Base58Utils.encode(String.valueOf(uid).getBytes());
    }
    @JsonCreator
    public Uid(String str) {
        byte[] b;
        try {
            b = Base58Utils.decode(str);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid UID");
        }
        long uid = Long.parseLong(new String(b));

        if (uid < (1L << 18)) {
            throw new IllegalArgumentException("Invalid UID");
        }

        this.localId = (int) ((uid >>> 28));
        this.typeId = (int) ((uid >>> 18) & 0x3FF);
        this.shardId = (int) (uid & 0x3FFF);
    }
}
