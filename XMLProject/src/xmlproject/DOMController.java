package xmlproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// FXML Controller class
public class DOMController implements Initializable {
    @FXML
    private TextField textName;
    @FXML
    private TextField textPhone;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textSearch;
    @FXML
    private Circle greenCircle;
    @FXML
    private Label labelCurrentStatus;
    @FXML
    private Button buttonInsert;

    DocumentBuilderFactory documentBuilderFactory;
    DocumentBuilder documentBuilder;
    Document document;
    static File file;
    Stage stage;
    NodeList nodeList;
    Node node;
    Element element;
    static int totalNumNodes;
    static int currentIndex;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMController.class.getName()).log(Level.SEVERE, null, ex);
        }
        totalNumNodes = 0;
        currentIndex = 0;
        file = findFile();
        parseXML();
        toggleTextFields();
        viewNode();
    }

    public void onPrev(ActionEvent event) {
        if (currentIndex > 0)
            currentIndex--;
        viewNode();
    }

    public void onNext(ActionEvent event) {
        if (currentIndex < totalNumNodes - 1)
            currentIndex++;
        viewNode();
    }

    public void onInsert(ActionEvent event) {
        if (buttonInsert.getText().equals("Insert")) {
            toggleTextFields();
            buttonInsert.setText("Insert Available");
            labelCurrentStatus.setText("Inserting");
            emptyTextFields();
        } else if (textName.getText().trim().isEmpty()) {
            labelCurrentStatus.setText("Name can't be empty");
        } else {
            toggleTextFields();
            buttonInsert.setText("Insert");
            labelCurrentStatus.setText("Inserted Succesfully");
            Element root = document.getDocumentElement();
            Element employee = document.createElement("employee");
            root.appendChild(employee);

            Element Name = document.createElement("Name");
            Element Phone = document.createElement("Phone");
            Element Email = document.createElement("Email");
            Element Address = document.createElement("Address");

            Name.appendChild(document.createTextNode(textName.getText().trim()));
            Phone.appendChild(document.createTextNode(textPhone.getText().trim()));
            Address.appendChild(document.createTextNode(textAddress.getText().trim()));
            Email.appendChild(document.createTextNode(textEmail.getText().trim()));

            employee.appendChild((Node) Name);
            employee.appendChild((Node) Phone);
            employee.appendChild((Node) Address);
            employee.appendChild((Node) Email);

            totalNumNodes++;
            labelCurrentStatus.setText("Inserted");
        }
    }

    public void onUpdate(ActionEvent event) {
        toggleTextFields();
        if (!textName.getText().trim().isEmpty() && totalNumNodes > 0) {
            labelCurrentStatus.setText("Updating");
            node = nodeList.item(currentIndex);
            element = (Element) node;

            element.getElementsByTagName("Name").item(0).setTextContent(textName.getText().trim());
            element.getElementsByTagName("Phone").item(0).setTextContent(textPhone.getText().trim());
            element.getElementsByTagName("Address").item(0).setTextContent(textAddress.getText().trim());
            element.getElementsByTagName("Email").item(0).setTextContent(textEmail.getText().trim());
        }
    }

    public void onDelete(ActionEvent event) {
        if (!textName.getText().trim().isEmpty()) {
            totalNumNodes--;
            node = nodeList.item(currentIndex);
            node.getParentNode().removeChild(node);
            emptyTextFields();
            onPrev(event);
            labelCurrentStatus.setText("Deleted Successfuly");
        } else {
            labelCurrentStatus.setText("There is nothing to delete!");
        }
    }

    // Searched name is case-sensitive
    public void onSearch(ActionEvent event) {
        boolean flag = false;
        String name;
        String nameInserted = textSearch.getText();
        for (int i = 0; i < totalNumNodes && flag == false; i++) {
            node = nodeList.item(i);
            element = (Element) node;
            name = element.getElementsByTagName("Name").item(0).getTextContent();
            if (name.startsWith(nameInserted)) {
                flag = true;
                currentIndex = i;
                viewNode();
            }
        }
        if (flag) {
            labelCurrentStatus.setText("Found at position: " + (currentIndex + 1));
        } else {
            labelCurrentStatus.setText("Not Found ");
        }
    }

    public void onReload(ActionEvent event) {
        parseXML();
        currentIndex = 0;
        viewNode();
        labelCurrentStatus.setText("Reloaded Succesfully");
    }

    public void onSave(ActionEvent event) {
        if (totalNumNodes <= 0)
            return;
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);
            currentIndex = 0;
            labelCurrentStatus.setText("Saved Successfully");
        } catch (TransformerException ex) {
            Logger.getLogger(DOMController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Private Methods
    private void parseXML() {
        try {
            document = documentBuilder.parse(file);
        } catch (SAXException | IOException ex) {
            Logger.getLogger(DOMController.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.getDocumentElement().normalize();
        nodeList = document.getElementsByTagName("employee");
        totalNumNodes = nodeList.getLength();
    }

    private void viewNode() {
        if (totalNumNodes > 0) {
            labelCurrentStatus.setText("Viewing node: " + (currentIndex + 1));
            node = nodeList.item(currentIndex);
            element = (Element) node;

            textName.setText(element.getElementsByTagName("Name").item(0).getTextContent());
            textPhone.setText(element.getElementsByTagName("Phone").item(0).getTextContent());
            textAddress.setText(element.getElementsByTagName("Address").item(0).getTextContent());
            textEmail.setText(element.getElementsByTagName("Email").item(0).getTextContent());
        }
    }

    // If xml file doesn't exist, createNewFile
    private File findFile() {
        file = new File("employees.xml");
        if (!file.isFile())
            createNewFile();
        return file;
    }

    private void createNewFile() {
        document = documentBuilder.newDocument();
        Element root = document.createElement("employees");
        document.appendChild(root);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("employees.xml"));
            transformer.transform(domSource, streamResult);
        } catch (TransformerException ex) {
            Logger.getLogger(DOMController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void toggleTextFields() {
        boolean currentState = textName.editableProperty().get();

        textName.setEditable(!currentState);
        textPhone.setEditable(!currentState);
        textAddress.setEditable(!currentState);
        textEmail.setEditable(!currentState);

        textName.setDisable(currentState);
        textPhone.setDisable(currentState);
        textAddress.setDisable(currentState);
        textEmail.setDisable(currentState);
        greenCircle.setVisible(!currentState);
        if (currentState == false)
            labelCurrentStatus.setText("Editing Entry");
        else
            labelCurrentStatus.setText("");
    }

    private void emptyTextFields() {
        textName.clear();
        textPhone.clear();
        textAddress.clear();
        textEmail.clear();
    }
}