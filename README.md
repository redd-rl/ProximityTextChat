# ProximityTextChat
A neat little Paper plugin to facilitate Proximity Text Chat between players!

# Configuration
## Settings 
- maximumReceivingDistance 
  - Default value is `30` blocks 
  - Maximum distance before other players lose the ability to hear you.
- globalPrefix 
  - Default value is `!`
  - The prefix to add before the message in order to make it visible for all players.
- cancelMessage 
  - Default value is `true` 
  - If the player's message should be canceled for them if nobody can hear them.

## Messages
Available placeholders are:  
`{player}` - the player's name.  
`{message}` - the player's message.  
Supports Minecraft's [color formatting system](https://minecraft.wiki/w/Formatting_codes).
- noReader = 
  - "&cNobody is around to hear you."
  - The returned message when nobody is around to hear a player's message.
- spyFormat 
  - "&d&lSPY&r &7» &a{player}: &7{message}"
  - The format to use for spy messages, requires permission: proximitytextchat.spy
- globalFormat
  - "&6&lGlobal: {player}&r &7» &r{message}"
  - The format to use for global messages, requires permission: proximitytextchat.global