package dad.geofx.client.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.geofx.client.ipapi.GeoIp;
import dad.geofx.client.ui.IpService;
import dad.geofx.client.ui.MyIpService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {
	private MyIpService miIp = new MyIpService();
	private IpService servicio = new IpService();

	private StringProperty ip = new SimpleStringProperty();
	private StringProperty latitudP = new SimpleStringProperty();
	private StringProperty longitudP = new SimpleStringProperty();
	private StringProperty locationP = new SimpleStringProperty();
	private ObjectProperty<Image> imagen = new SimpleObjectProperty<Image>();
	private StringProperty countryCity = new SimpleStringProperty();
	private StringProperty zip = new SimpleStringProperty();
	private StringProperty codeWet = new SimpleStringProperty();
	private StringProperty callingCodeP = new SimpleStringProperty();
	private StringProperty currency = new SimpleStringProperty();
	private StringProperty lenguaje = new SimpleStringProperty();
	private StringProperty ipP = new SimpleStringProperty();
	private StringProperty isp = new SimpleStringProperty();
	private StringProperty ipv = new SimpleStringProperty();
	private StringProperty asnP = new SimpleStringProperty();
	private StringProperty host = new SimpleStringProperty();
	private StringProperty level = new SimpleStringProperty();
	private BooleanProperty proxy = new SimpleBooleanProperty();
	private BooleanProperty crawler = new SimpleBooleanProperty();
	private BooleanProperty tor = new SimpleBooleanProperty();

	private Task<GeoIp> task;

	@FXML
	private Label asn;

	@FXML
	private Label callingCode;

	@FXML
	private Button checkButton;

	@FXML
	private Label city;

	@FXML
	private CheckBox crawlerCheck;

	@FXML
	private Label hostName;

	@FXML
	private ImageView imagenPais;

	@FXML
	private Label ipAddress;

	@FXML
	private Label ipRegister;

	@FXML
	private Label latitud;

	@FXML
	private Label lenguage;

	@FXML
	private Label location;

	@FXML
	private Label longitud;

	@FXML
	private Label moneda;

	@FXML
	private TextField numeroIp;

	@FXML
	private CheckBox proxyCheck;

	@FXML
	private Label timeZone;

	@FXML
	private CheckBox torDetected;

	@FXML
	private Label type;

	@FXML
	private VBox view;

	@FXML
	private Label zipCode;

	@FXML
	private Label levelThreat;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		numeroIp.textProperty().bindBidirectional(ip);
		latitud.textProperty().bind(latitudP);
		longitud.textProperty().bind(longitudP);
		this.location.textProperty().bind(locationP);
		imagenPais.imageProperty().bind(imagen);
		city.textProperty().bind(countryCity);
		zipCode.textProperty().bind(zip);
		timeZone.textProperty().bind(codeWet);
		callingCode.textProperty().bind(callingCodeP);
		moneda.textProperty().bind(currency);
		lenguage.textProperty().bind(lenguaje);
		ipAddress.textProperty().bind(ipP);
		ipRegister.textProperty().bind(isp);
		type.textProperty().bind(ipv);
		asn.textProperty().bind(asnP);
		hostName.textProperty().bind(host);
		levelThreat.textProperty().bind(level);
		proxyCheck.selectedProperty().bind(proxy);
		crawlerCheck.selectedProperty().bind(crawler);
		torDetected.selectedProperty().bind(tor);
		try {
			ip.set(miIp.myIp());
			datosIp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view.fxml"));
		loader.setController(this);
		loader.load();
	}

	public VBox getView() {
		return view;
	}

	@FXML
	void onCheckButton(ActionEvent event) throws Exception {
		datosIp();
	}

	private void datosIp() throws IOException {
		task = new Task<GeoIp>() {

			@Override
			protected GeoIp call() throws Exception {
				return servicio.getGeoIp(ip.get());
			}
		};

		task.setOnSucceeded(ev -> {
			try {
				longitudP.set("" + task.getValue().getLongitude());
				latitudP.set("" + task.getValue().getLatitude());
				locationP.set(task.getValue().getCountryName() + " (" + task.getValue().getCountryCode() + ")");
				imagen.set(new Image(getClass().getResource("/images/64x42/" + task.getValue().getCountryCode() + ".png")
						.toExternalForm()));
				countryCity.set(task.getValue().getCity() + " (" + task.getValue().getCountryName() + ")");
				zip.set(task.getValue().getZip());
				codeWet.set(task.getValue().getTimeZone().getCode());
				callingCodeP.set("+" + task.getValue().getLocation().getCallingCode());
				currency.set(
						task.getValue().getCurrency().getCode() + " (" + task.getValue().getCurrency().getSymbol() + ")");
				lenguaje.set(task.getValue().getLocation().getLanguages().get(0).getName() + " ("
						+ task.getValue().getLocation().getLanguages().get(0).getCode() + ")");
				ipP.set(ip.get());
				isp.set(task.getValue().getConnection().getIsp());
				ipv.set(task.getValue().getType());
				asnP.set(task.getValue().getConnection().getAsn() + "");
				host.set(task.getValue().getHostname());
				proxy.set(task.getValue().getSecurity().getIsProxy());
				crawler.set(task.getValue().getSecurity().getIsCrawler());
				tor.set(task.getValue().getSecurity().getIsTor());
				level.set(task.getValue().getSecurity().getThreatLevel());
			} catch (Exception e) {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.initOwner(view.getScene().getWindow());
				alerta.setContentText("Invalid message supplied");
				alerta.setTitle("Error");
				alerta.setHeaderText("Error de carga de datos");
				alerta.showAndWait();
			}
		});

		task.setOnFailed(ev -> {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.initOwner(view.getScene().getWindow());
			alerta.setContentText("Invalid message supplied");
			alerta.setTitle("Error");
			alerta.setHeaderText("Error de IP");
			alerta.showAndWait();
		});
		new Thread(task).start();
	}
}
