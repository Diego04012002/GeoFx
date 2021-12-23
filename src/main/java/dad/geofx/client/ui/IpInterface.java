package dad.geofx.client.ui;

import dad.geofx.client.ipapi.GeoIp;
import dad.geofx.client.ipapi.Language;
import dad.geofx.client.ipapi.Location;
import dad.geofx.client.ipapi.TimeZone;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IpInterface {
	
	@GET("ip_api.php/")
	public Call<GeoIp> getGeoIp(@Query("ip") String ip);

}
