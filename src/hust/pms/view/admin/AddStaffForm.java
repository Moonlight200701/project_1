package hust.pms.view.admin;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import hust.pms.controller.CompanyController;
import hust.pms.controller.EmployeeController;
import hust.pms.controller.ParkingController;
import hust.pms.controller.RoleController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import hust.pms.view.AccountHelper;
import hust.pms.view.LabelHelper;
import hust.pms.view.TextFieldHelper;
import hust.util.AuthService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class AddStaffForm implements Initializable, LabelHelper, TextFieldHelper {

	SceneController sceneRoute = SceneController.getInstance();
	EmployeeController empController = new EmployeeController();
	CompanyController comController = new CompanyController();
	ParkingController parkController = new ParkingController();
	RoleController roleController = new RoleController();

	@FXML
	private TextField tfName;

	@FXML
	private ComboBox<String> comboGender;

	@FXML
	private ComboBox<String> comboCompany;

	@FXML
	private ComboBox<String> comboParking;

	@FXML
	private ComboBox<String> comboRole;

	@FXML
	private TextField tfPhone;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextField tfEmail;

	@FXML
	private TextField tfAddress;

	@FXML
	private TextField tfUsername;

	@FXML
	private PasswordField pfPassword;

	@FXML
	private Button btAdd;

	@FXML
	private Button btClear;

	@FXML
	private Button btClose;

	@FXML
	private Label labelNotice;

	@FXML
	private void btAddAction() throws SQLException {
		Employee emp = new Employee();
		if (tfName.getText() == null || tfName.getText().trim().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Please fill your full name.");
			System.out.println("name is empty");
			return;
		} else {
			emp.setName(tfName.getText());
		}
		if (comboGender.getSelectionModel().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Please fill your gender.");
			return;
		} else {
			emp.setGender(comboGender.getValue());
		}
		
		
		datePicker.setConverter(new StringConverter<LocalDate>() {

			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
		if (datePicker.getValue() == null) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Please fill your date of birth.");
			return;
		} else {
			emp.setBirthDate(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}

		if (tfPhone.getText() == null || tfPhone.getText().trim().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Please fill your phone number.");
			System.out.println("phone number is empty");
			return;
		} else if (AccountHelper.validatePhoneNumber(tfPhone.getText())) {
			emp.setPhoneNumber(tfPhone.getText());
		} else {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Invalid phone number format.");
			System.out.println("Invalid phone number format.");
			return;
		}

		if (tfEmail.getText() == null || tfEmail.getText().trim().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Please fill your email.");
			System.out.println("Please fill your email.");
			return;
		} else if (AccountHelper.validateEmail(tfEmail.getText())) {
			emp.setEmail(tfEmail.getText());
		} else {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Invalid email format.");
			System.out.println("Invalid email format.");
			return;
		}

		emp.setAddress(tfAddress.getText());

		if (tfUsername.getText().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Username is required.");
			return;
		} else {
			emp.setUsername(tfUsername.getText());
		}
		

		if (pfPassword.getText().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Password is required.");
		} else {
			emp.setPassword(AuthService.getInstance().bEncrypt(pfPassword.getText()));
		}
		

		System.out.println("companyname=" + comboCompany.getValue());
	
		emp.setCompanyID(comController.getCompanyIDByCompanyName(comboCompany.getValue().toString()));
		if (comboRole.getSelectionModel().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Missing Role.");
			return;
		} else {
			emp.setRoleID(roleController.getRoleNo(comboRole.getValue().toString()));
		}
		
		if (comboParking.getSelectionModel().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			labelNotice.setText("Missing Parking.");
			return;
		} else {
			emp.setParkingID(parkController.getParkingID(comboParking.getValue().toString()));
		}

		try {
			if (empController.isEmailExist(tfEmail.getText())) {
				labelNotice.setTextFill(Color.RED);
				labelNotice.setText("Email is exist.");
				System.err.println("Email is exist.");
				return;
			} else if (empController.isPhoneNumberExist(tfPhone.getText())) {
				labelNotice.setTextFill(Color.RED);
				labelNotice.setText("Phone number is exist.");
				System.err.println("Phone number is exist.");
				return;
			} else {
				empController.addStaff(emp);
				labelNotice.setTextFill(Color.GREEN);
				labelNotice.setText("Add staff successfully.");
				SceneController.getInstance().adminStaffForm.refreshTable();
			}
		} catch (ClassNotFoundException cnfe) {
//			cnfe.printStackTrace();
		} catch (SQLException sqle) {
//			sqle.printStackTrace();
			sceneRoute.toAlertWithTitleAndContent("Error when adding Staff", sqle.toString());
		}
	}

	@FXML
	private void btClearAction(ActionEvent event) {
		tfName.clear();
		comboGender.setValue(null);
		datePicker.setValue(null);
		tfEmail.clear();
		tfPhone.clear();
		tfAddress.clear();
		tfUsername.clear();
		pfPassword.clear();
		comboCompany.setValue(null);
		comboParking.setValue(null);
		comboRole.setValue(null);
	}

	@FXML
	private void btCloseAction(ActionEvent event) {
		sceneRoute.closeSceneWithStageRelatedButton(btClose);
	}

	private void loadComboBox() throws SQLException {
		final ObservableList<String> comboGenderList = FXCollections.observableArrayList("Male", "Female",
				"Unknow");
		final ObservableList<String> comboCompanyObList = FXCollections
				.observableArrayList(comController.getCompanyNameToLoadComboBox());
		final ObservableList<String> comboParkingObList = FXCollections
				.observableArrayList(parkController.getParkingNameBelongToCompanyToLoadComboBox());
		final ObservableList<String> comboRoleObList = FXCollections
				.observableArrayList(roleController.getRoleNameToLoadComboBox());
		CompanyController cc = new CompanyController();
		comboGender.setItems(comboGenderList);
		comboCompany.getSelectionModel().select(cc.getCurrentEmployeeCompanyFromCompanyID());
		comboParking.setItems(comboParkingObList);
		comboRole.setItems(comboRoleObList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			loadComboBox();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void allFilled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void allCorrectFormat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearLabel(Label label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearField(TextField textField) {
		// TODO Auto-generated method stub
		
	}

}
