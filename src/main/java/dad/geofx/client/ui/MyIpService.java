package dad.geofx.client.ui;

import java.util.concurrent.TimeUnit;

import dad.geofx.client.myip.ExampleMyIp;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyIpService {

	private static final String BASE_URL ="https://api.ipify.org/";
	
	private MyIpInterface service;
	
	public MyIpService() {

		ConnectionPool pool = new ConnectionPool(1, 5, TimeUnit.SECONDS);

		OkHttpClient client = new OkHttpClient.Builder()
				.connectionPool(pool)
				.build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		service = retrofit.create(MyIpInterface.class);
		
	}
	
	public String myIp() throws Exception {
		Response<ExampleMyIp> response = service.getIp("json").execute();
		ExampleMyIp message= response.body();
		return message.getIp();
	}

}
