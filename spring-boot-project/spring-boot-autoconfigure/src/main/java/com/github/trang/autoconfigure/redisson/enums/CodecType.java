package com.github.trang.autoconfigure.redisson.enums;

import org.redisson.cache.LocalCachedMessageCodec;
import org.redisson.client.codec.BitSetCodec;
import org.redisson.client.codec.ByteArrayCodec;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.DoubleCodec;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.codec.JsonJacksonMapCodec;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.AvroJacksonCodec;
import org.redisson.codec.CborJacksonCodec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.IonJacksonCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.KryoCodec;
import org.redisson.codec.LZ4Codec;
import org.redisson.codec.MsgPackJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.redisson.codec.SmileJacksonCodec;
import org.redisson.codec.SnappyCodec;
import org.redisson.codec.SnappyCodecV2;

/**
 * 序列化方式
 *
 * https://github.com/redisson/redisson/wiki/4.-数据序列化
 *
 * @author trang
 */
public enum CodecType {

    JACKSON {
        @Override
        public Codec getInstance() {
            return JsonJacksonCodec.INSTANCE;
        }
    },

    JACKSON_MAP {
        @Override
        public Codec getInstance() {
            return JsonJacksonMapCodec.INSTANCE;
        }
    },

    SMILE_JACKSON {
        @Override
        public Codec getInstance() {
            return SmileJacksonCodec.INSTANCE;
        }
    },

    AVRO_JACKSON {
        @Override
        public Codec getInstance() {
            return AvroJacksonCodec.INSTANCE;
        }
    },

    ION_JACKSON {
        @Override
        public Codec getInstance() {
            return IonJacksonCodec.INSTANCE;
        }
    },

    CBOR_JACKSON {
        @Override
        public Codec getInstance() {
            return CborJacksonCodec.INSTANCE;
        }
    },

    MSG_PACK_JACKSON {
        @Override
        public Codec getInstance() {
            return MsgPackJacksonCodec.INSTANCE;
        }
    },

    JDK {
        @Override
        public Codec getInstance() {
            return new SerializationCodec();
        }
    },

    KRYO {
        @Override
        public Codec getInstance() {
            return new KryoCodec();
        }
    },

    FST {
        @Override
        public Codec getInstance() {
            return new FstCodec();
        }
    },

    LZ4 {
        @Override
        public Codec getInstance() {
            return new LZ4Codec();
        }
    },

    SNAPPY {
        @Override
        public Codec getInstance() {
            return new SnappyCodec();
        }
    },

    SNAPPY_V2 {
        @Override
        public Codec getInstance() {
            return new SnappyCodecV2();
        }
    },

    STRING {
        @Override
        public Codec getInstance() {
            return StringCodec.INSTANCE;
        }
    },

    DOUBLE {
        @Override
        public Codec getInstance() {
            return DoubleCodec.INSTANCE;
        }
    },

    INTEGER {
        @Override
        public Codec getInstance() {
            return IntegerCodec.INSTANCE;
        }
    },

    LONG {
        @Override
        public Codec getInstance() {
            return LongCodec.INSTANCE;
        }
    },

    BYTE_ARRAY {
        @Override
        public Codec getInstance() {
            return ByteArrayCodec.INSTANCE;
        }
    },

    BIT_SET {
        @Override
        public Codec getInstance() {
            return BitSetCodec.INSTANCE;
        }
    },

    LOCAL_CACHED_MESSAGE {
        @Override
        public Codec getInstance() {
            return LocalCachedMessageCodec.INSTANCE;
        }
    },

    ;

    public abstract Codec getInstance();

}