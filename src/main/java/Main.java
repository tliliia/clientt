import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.cell.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import sample.Contract;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Main extends Application {
    private ObservableList<Contract> data;
    public static final String CONTRACTS_API_URL = "http://localhost:8081/contracts/";
    private Contract model;
    private HttpClient client = HttpClient.newHttpClient();

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        TableView<Contract> table = new TableView<Contract>();

        TableColumn<Contract, Integer> contractNumberCol = new TableColumn<Contract, Integer>("id");
        TableColumn<Contract, String> createdDateCol = new TableColumn<Contract, String>("created");
//        TableColumn<Contract, Boolean> actualCol = new CheckBoxTableCell<Contract, Boolean>("Актуальность");

        // Defines how to fill data for each cell.
        contractNumberCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        createdDateCol.setCellValueFactory(new PropertyValueFactory<>("created"));
//        actualCol.setCellFactory(tc -> new CheckBoxTableCell<>());
//        actualCol.setCellValueFactory(f -> f.getValue().getIsActual());

        // Display row data
        ObservableList<Contract> data = getData();
        table.setItems(data);

        table.getColumns().addAll(contractNumberCol, createdDateCol);//, actualCol);

        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(table);

        stage.setTitle("TableView (o7planning.org)");

        Scene scene = new Scene(root, 450, 300);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Contract> getData() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(CONTRACTS_API_URL))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // parse JSON
        ObjectMapper mapper = new ObjectMapper();
        List<Contract> posts = mapper.readValue(response.body(), new TypeReference<List<Contract>>() {});
        return FXCollections.observableList(posts);
    }

      public static void main(String[] args) {
        launch(args);
    }
}
