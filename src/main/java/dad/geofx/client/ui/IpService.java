package dad.geofx.client.ui;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dad.geofx.client.ipapi.GeoIp;
import dad.geofx.client.ipapi.Location;
import dad.geofx.client.ipapi.TimeZone;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IpService {

	private static final String BASE_URL = "https://ipapi.com/";

	private IpInterface service;

	public IpService() {
		ConnectionPool pool = new ConnectionPool(1, 5, TimeUnit.SECONDS);

		OkHttpClient client = new OkHttpClient.Builder().connectionPool(pool).build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client)
				.addConverterFactory(GsonConverterFactory.create()).build();

		service = retrofit.create(IpInterface.class);
	}

	public GeoIp getGeoIp(String ip) throws IOException {
		return service.getGeoIp(ip).execute().body();
	}
}
