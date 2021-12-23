package dad.geofx.client.ui;

import dad.geofx.client.myip.ExampleMyIp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyIpInterface {
	
	@GET("/")
	public Call<ExampleMyIp> getIp(@Query("format") String format);
}