package net.valneas.account.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.valneas.account.bungee.mongo.Mongo;

import java.io.*;

public class AccountBungee extends Plugin {

    private Mongo mongo;
    private Configuration config;

    @Override
    public void onEnable() {
        createConfig();
        mongo = new Mongo(this);
    }

    private void createConfig(){
        String resourcePath = "config.yml", outputPath = "config.yml";

        InputStream in = getResourceAsStream(resourcePath);
        if(in == null){
            throw new IllegalArgumentException("The resource '" + resourcePath + "' cannot be found in plugin jar");
        }

        if(!getDataFolder().exists() && !getDataFolder().mkdirs()){
            getLogger().severe("Failed to make directory");
        }

        File outFile = new File(getDataFolder(), outputPath);

        if(!outFile.exists()){
            getLogger().info("The " + resourcePath + " was not found, creation in progress...");

            try {
                outFile.createNewFile();
                OutputStream out = new FileOutputStream(outFile);

                byte[] buf = new byte[1024];
                int n;

                while((n = in.read(buf)) >= 0){
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                if(outFile.exists()){
                    getLogger().severe("Unable to copy file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }

    /**
     * Get a mongo class instance
     * @return mongo class instance
     */
    public Mongo getMongo() {
        return mongo;
    }
}
