package git.dimitrikvirik.chessgameapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean(name = ["redisTemplate"])
    fun redisTemplate(redisConnectionFactory: LettuceConnectionFactory?): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.setConnectionFactory(redisConnectionFactory!!)
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.setDefaultSerializer(StringRedisSerializer())
        redisTemplate.setEnableTransactionSupport(true)
        return redisTemplate
    }
}