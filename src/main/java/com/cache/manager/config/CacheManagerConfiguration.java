package com.cache.manager.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheManagerConfiguration {

    /*@Bean
	Config config() {
		Config config = new Config();
		config.setInstanceName("cache-1");
		config.setClusterName("dev");
		ManagementCenterConfig mcc = new ManagementCenterConfig()
				.setConsoleEnabled(true);
		config.setManagementCenterConfig(mcc);
		SerializerConfig sc = new SerializerConfig()
				.setTypeClass(Employee.class)
				.setClass(EmployeeSerializer.class);
		config.getSerializationConfig().addSerializerConfig(sc);
		return config;
	}*/

	/*@Bean
	public Config config() {
		Config config = new Config();
		JoinConfig joinConfig = config.getNetworkConfig().getJoin();
		joinConfig.getTcpIpConfig().setEnabled(false);
		joinConfig.getMulticastConfig().setEnabled(false);
		joinConfig.getKubernetesConfig().setEnabled(true)
				.setProperty("namespace", "development")
				.setProperty("service-name", "hazelcast-service");
		return config;
	}*/


    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();

        // Set the shutdown hook policy to TERMINATE
        config.setProperty("hazelcast.shutdownhook.policy", "TERMINATE");

        config.getNetworkConfig().setPortAutoIncrement(true);
//		config.getGroupConfig().setName("hazelcast-cluster");
//		config.setManagementCenterConfig(new ManagementCenterConfig().setEnabled(true).setUrl("http://localhost:8080/hazelcast-mancenter"));
        NetworkConfig network = config.getNetworkConfig();
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(true);

        // Time-to-Live Configuration for a Map
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("employee")
                .setTimeToLiveSeconds(300); // TTL in seconds (1 hour)
        config.addMapConfig(mapConfig);

        return config;
    }

	/*@Bean
	public HazelcastInstance hazelcastInstance(Config config) {
		return Hazelcast.newHazelcastInstance(config);
	}*/

	/*@Bean
	HazelcastInstance hazelcastInstance() {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(hazelCastConfig());
		instance.getConfig().addMapConfig(new MapConfig("employee").setTimeToLiveSeconds(120));
		return instance;
	}*/

	/*@Bean
	public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
		return Hazelcast.newHazelcastInstance(hazelCastConfig);
	}*/
}
