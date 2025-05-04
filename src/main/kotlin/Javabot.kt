import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.integer
import dev.kord.rest.builder.interaction.string
import io.github.cdimascio.dotenv.dotenv

class Javabot {
    private val urlBase = "https://adoptium.net/temurin/releases/?"
    private lateinit var pathingChannel: Snowflake

    suspend fun main() {
        val dotenv = dotenv()
        val guildId = Snowflake(dotenv["GUILD_ID"])
        val bot = Kord(dotenv["TOKEN"])
        pathingChannel = Snowflake(dotenv["PATHING_CHANNEL_ID"])

        bot.createGuildChatInputCommand(guildId, "adoptium", "Display proper Adoptium versions for various computer setups") {
            integer("version", "Java version to display") { required = true }
            string("cfg", "computer configuration to display") { required = false }
        }

        bot.on<GuildChatInputCommandInteractionCreateEvent> {
            val response = interaction.deferPublicResponse()
            val command = interaction.command
            val cfg = command.strings["cfg"] ?: "DEFAULT"
            response.respond {
                content = generateContent(cfg)
            }
        }
    }

    private fun generateContent(cfg: String): String {
        val configValue = enumValues<Configurations>().firstOrNull() { it.name == cfg.uppercase() } ?: Configurations.DEFAULT
        if (configValue != Configurations.LINUX_AMD64) {
            return """
                **Uninstall other Java Versions** by searching java/jdk through the **apps and features** menu.
                
                If you need multiple Java versions use <#$pathingChannel> instead
                
                **Minecraft 1.16.5/1.12.2** requires Java 8,
                                    
                - Download ${configValue.ext} and run the installer
                ${urlBase + "version=8&${configValue.cfg}&${configValue.arch}&package=jre"}
                
                **Minecraft 1.17 and newer** requires Java 17
                
                - Download ${configValue.ext} and run the installer
                ${urlBase + "version=17&${configValue.cfg}&${configValue.arch}&package=jre"}
            """.trimIndent()
        } else {
            return """
                **Minecraft 1.16.5/1.12.2** requires Java 8,
                                    
                ${urlBase + "version=8&${configValue.cfg}&${configValue.arch}&package=jre"}
                
                **Minecraft 1.17 and newer** requires Java 17
                
                ${urlBase + "version=17&${configValue.cfg}&${configValue.arch}&package=jre"}
            """.trimIndent()
        }
    }
}