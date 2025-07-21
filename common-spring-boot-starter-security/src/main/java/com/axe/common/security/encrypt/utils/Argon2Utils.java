package com.axe.common.security.encrypt.utils;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Description: TODO
 * @Date: 2025/7/21
 * @Author: Sxt
 * @Version: v1.0
 */
public class Argon2Utils {

    private static final int ITERATIONS = 10;
    private static final int MEMORY_KB = 65536;
    private static final int PARALLELISM = 4;
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private static final int TYPE = Argon2Parameters.ARGON2_id;


    /**
     * 生成 Argon2 哈希
     * @param password 待哈希的密码
     * @return 格式: "哈希值|Base64盐值|迭代次数|内存大小|并行度|类型|哈希长度"
     */
    public static String hashPassword(char[] password) {
        // 1. 生成随机盐值
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        // 2. 配置 Argon2 参数
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(TYPE)
                .withSalt(salt)
                .withIterations(ITERATIONS)
                .withMemoryAsKB(MEMORY_KB)
                .withParallelism(PARALLELISM);
        // 3. 执行哈希计算
        byte[] hash = new byte[HASH_LENGTH];
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());
        generator.generateBytes(password, hash);
        // 4. 构建可存储的字符串
        return Base64.getEncoder().encodeToString(hash) + "|" +
                Base64.getEncoder().encodeToString(salt) + "|" +
                ITERATIONS + "|" +
                MEMORY_KB + "|" +
                PARALLELISM + "|" +
                TYPE + "|" +
                HASH_LENGTH;
    }

    /**
     * 验证密码
     * @param password 待验证密码
     * @param storedHash 存储的完整哈希字符串
     * @return 是否验证通过
     */
    public static boolean verifyPassword(char[] password, String storedHash) {
        // 1. 解析存储的哈希字符串
        String[] parts = storedHash.split("\\|");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid hash format");
        }
        byte[] hash = Base64.getDecoder().decode(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        int iterations = Integer.parseInt(parts[2]);
        int memory = Integer.parseInt(parts[3]);
        int parallelism = Integer.parseInt(parts[4]);
        int type = Integer.parseInt(parts[5]);
        int hashLength = Integer.parseInt(parts[6]);
        // 2. 使用相同参数重新计算哈希
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(type)
                .withSalt(salt)
                .withIterations(iterations)
                .withMemoryAsKB(memory)
                .withParallelism(parallelism);

        byte[] testHash = new byte[hashLength];
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());
        generator.generateBytes(password, testHash);

        // 3. 安全比较哈希值
        return constantTimeEquals(hash, testHash);
    }

    /**
     * 恒定时间比较（防止时序攻击）
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
