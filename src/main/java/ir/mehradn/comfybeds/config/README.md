# Configurations Guide
This mod uses [MehradConfig](https://github.com/MehradN/MehradConfig) for configurations. The main file to edit for the configurations is located at `.minecraft/config/comfy-beds.json`. The mod provides a GUI config screen that you can use if you have a mod such as ModMenu installed. I suggest you create your configurations using the GUI on a Minecraft client, and the copying the configuration file to your server. <br>
Reloading the server after updating the config is required for the changes to apply.

### Rest At Day (`allowRestAtDay`)
If enabled, you are allowed to rest during the day. However, the day will not be skipped if it's before nighttime.

### Set Respawn (`changeRespawn`)
- **Normal (`NORMAL`):** Same as vanilla, when you right-click a bed.
- **/setbed (`COMMAND`, Default):** When you use the /setbed command while you are resting in a bed.
- **Shift Click (`SHIFT_CLICK`):** When you shift-clicking a bed.
- **Don't Shift Click (`NOT_SHIFT_CLICK`):** The opposite of the "Shift Click" option, when you right-click the bed without shifting.

### Instructions (`provideInstructions`)
If enabled, you will be provided with some instructions about setting your respawn point when you rest in a bed. <br>
Running the `/setbed` command while being outside of a bed will provide you with the instructions, even if this option is disabled.

### Beds In Nether (`outsideOverworld`)
- **Explode (`EXPLODE`):** Same as vanilla, beds will explode.
- **Monsters Nearby (`MONSTERS`, Default):** You may not rest now; there are monsters nearby.
- **Rest (`REST`):** You will be allowed to rest in the nether, without setting your respawn point.
