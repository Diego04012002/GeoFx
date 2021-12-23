package dad.geofx.client.ui;

public class Main {

	public static void main(String[] args) throws Exception {
		MyIpService service = new MyIpService();
		IpService service2 = new IpService();

		System.out.println(service.myIp());

	}
}
