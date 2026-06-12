package com.pratica4.application.gui;

import com.jfoenix.controls.JFXButton;
import com.pratica4.application.MainApplication;
import com.pratica4.application.dao.AnimalDAO;
import com.pratica4.application.dao.ClienteDAO;
import com.pratica4.application.dao.RacaDAO;
import com.pratica4.application.models.Animal;
import com.pratica4.application.models.Cliente;
import com.pratica4.application.models.Identificador;
import com.pratica4.application.models.Raca;
import com.pratica4.application.utils.NavigationManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

public class ConsultaController {

    @FXML private TextField searchField;
    @FXML private StackPane dynamicTableContainer;

    @FXML private VBox tableContainer;
    @FXML private TableView<Identificador> table;

    // CAMPOS EM COMUM DAS TABELAS
    @FXML private TableColumn<Identificador, Boolean> statusColumn;
    @FXML private TableColumn<Identificador, Integer> idColumn;
    @FXML private TableColumn<Identificador, String > nameColumn;
    @FXML private TableColumn<Identificador, Void> actionColumn;

    // CAMPOS DA TABELA CLIENTE

    @FXML private TableColumn<Cliente, String > addressColumn;
    @FXML private TableColumn<Cliente, String > cepColumn;
    @FXML private TableColumn<Cliente, String > cityColumn;
    @FXML private TableColumn<Cliente, String > cpfColumn;
    @FXML private TableColumn<Cliente, Date> dateColumn;

    @FXML private TableColumn<Cliente, String > neighColumn;
    @FXML private TableColumn<Cliente, String > phoneColumn;
    @FXML private TableColumn<Cliente, String > stateColumn;


    // CAMPOS DA TABELA ANIMAL
    @FXML private TableColumn<Animal, Integer> animalIdColumn;
    @FXML private TableColumn<Animal, String> animalNameColumn;
    @FXML private TableColumn<Animal, Raca> raceColumn;
    @FXML private TableColumn<Animal, Date> ageColumn;
    @FXML private TableColumn<Animal, String> sexColumn;
    @FXML private TableColumn<Animal, String> animalStatusColumn;

    // CAMPOS DA TABELA RAÇA
    @FXML private TableColumn<Raca, Integer> raceIdColumn;
    @FXML private TableColumn<Raca, String> raceNameColumn;
    @FXML private TableColumn<Raca, String> typeColumn;
    @FXML private TableColumn<Raca, String> raceStatusColumn;




    private ObservableList<Identificador> listaObservavel = FXCollections.observableArrayList();
    private final Image iconEdit = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pratica4/application/img/icon-edit.png")));
    private final Image iconDelete = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pratica4/application/img/icon-close.png")));

    private boolean isAlreadyInitialized = false;

    public void initialize() {
//        loadClients();
//        setTableData(listaObservavel);
        if (isAlreadyInitialized) {
            return;
        }

        isAlreadyInitialized = true;
        Platform.runLater(this::handleShowClientTable);
    }

    @FXML
    private void handleShowTable(String url, Runnable load) {
        try {
            dynamicTableContainer.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    MainApplication.class.getResource("views/" + url + ".fxml")));
            loader.setController(this);
            table = loader.load();

            dynamicTableContainer.getChildren().add(table);

            listaObservavel.clear();
            load.run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowClientTable() {
        handleShowTable("tabela-cliente", this::loadClients);
    }

    @FXML
    private void handleShowAnimalTable() {
        System.out.println("Tabela de Animais");
        handleShowTable("tabela-animal", this::loadAnimals);
    }

    @FXML
    private void handleShowRaceTable() {
        System.out.println("Tabela de Raças");
        handleShowTable("tabela-raca", this::loadRaces);
    }

    private void loadClients() {
        ClienteDAO dao = new ClienteDAO();

        table.setPlaceholder(new Label("Carregando clientes..."));

        Task<List<Cliente>> loadClientsTask = new Task<>() {
            @Override
            protected List<Cliente> call() throws Exception {
                return dao.viewClients();
            }
        };

        loadClientsTask.setOnSucceeded(e -> Platform.runLater(() -> {
            listaObservavel.clear();
            listaObservavel.addAll(loadClientsTask.getValue());
            setTableData(listaObservavel);
        }));

        loadClientsTask.setOnFailed(e -> Platform.runLater(() -> {
            table.setPlaceholder(new Label("Erro ao carregar tabela: " + loadClientsTask.getException().getMessage()));
        }));

        Thread thread = new Thread(loadClientsTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void loadAnimals() {
        AnimalDAO dao = new AnimalDAO();
        table.setPlaceholder(new Label("Carregando Animais..."));

        Task<List<Animal>> loadAnimalsTask = new Task<>() {
            @Override
            protected List<Animal> call() throws Exception {
                return dao.viewAnimals();
            }
        };

        loadAnimalsTask.setOnSucceeded(e -> Platform.runLater(() -> {
            listaObservavel.clear();
            listaObservavel.addAll(loadAnimalsTask.getValue());
            setTableData(listaObservavel);
        }));

        loadAnimalsTask.setOnFailed(e -> Platform.runLater(() -> {
            table.setPlaceholder(new Label("Erro ao carregar tabela: " + loadAnimalsTask.getException().getMessage()));
        }));

        Thread thread = new Thread(loadAnimalsTask);
        thread.setDaemon(true);
        thread.start();

    }
    private void loadRaces() {
        RacaDAO dao = new RacaDAO();
        table.setPlaceholder(new Label("Carregando Raças..."));
        Task<List<Raca>> loadRacaTask = new Task<>() {
            @Override
            protected List<Raca> call() throws Exception {
                return dao.viewRaca();
            }
        };

        loadRacaTask.setOnSucceeded(e -> Platform.runLater(() -> {
            listaObservavel.clear();
            listaObservavel.addAll(loadRacaTask.getValue());
            setTableData(listaObservavel);
        }));

        loadRacaTask.setOnFailed(e -> Platform.runLater(() -> {
            table.setPlaceholder(new Label("Erro ao carregar tabela: " + loadRacaTask.getException().getMessage()));
        }));

        Thread thread = new Thread(loadRacaTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void setTableData(ObservableList<Identificador> lista) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        if (cpfColumn != null) {
            cpfColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<Cliente, Date>("data_nascimento"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("endereco"));
            neighColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("bairro"));
            cityColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cidade"));
            stateColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("estado"));
            cepColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cep"));
        }

        if(sexColumn != null) {
            raceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRaca()));
            ageColumn.setCellValueFactory(new PropertyValueFactory<Animal, Date>("data_nascimento"));
            sexColumn.setCellValueFactory(new PropertyValueFactory<Animal, String>("sexo"));

            ageColumn.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Animal, Date> call(TableColumn<Animal, Date> param) {
                    return new TableCell<>() {
                        @Override
                        protected void updateItem(Date item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                try {
                                    LocalDate dataNasc = item.toLocalDate();
                                    LocalDate dataAtual = LocalDate.now();

                                    Period periodo = Period.between(dataNasc, dataAtual);

                                    String idade;

                                    if (periodo.getYears() > 0) {
                                        idade = periodo.getYears() + (periodo.getYears() == 1 ? " Ano" : " Anos");
                                    } else if (periodo.getMonths() > 0) {
                                        idade = periodo.getMonths() + (periodo.getMonths() == 1 ? " Mês" : " Meses");
                                    } else {
                                        idade = periodo.getDays() + (periodo.getDays() == 1 ? " Dia" : " Dias");
                                    }

                                    setText(idade);
                                } catch(Exception e) {
                                    setText(item.toString());
                                }
                            }
                        }
                    };
                }
            });

            raceColumn.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Animal, Raca> call(TableColumn<Animal, Raca> param) {
                    return new TableCell<>() {
                        @Override
                        protected void updateItem(Raca item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.getNome() + " | " + item.getTipo());
                            }
                        }
                    };
                }
            });
        }

        if(typeColumn != null) {
            typeColumn.setCellValueFactory(new PropertyValueFactory<Raca, String>("tipo"));
        }

        statusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Identificador, Boolean> call(TableColumn<Identificador, Boolean> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Label status = new Label("teste");
                            if (item) {
                                status.setText("Ativo");
                                status.getStyleClass().addAll("status", "status--active");
                                setGraphic(status);
                            } else {
                                status.setText("Inativo");
                                status.getStyleClass().addAll("status", "status--inactive");
                                setGraphic(status);
                            }
                        }
                    }
                };
            }
        });


        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Identificador, Void> call(final TableColumn<Identificador, Void> param) {
                return new TableCell<>() {
                    private final JFXButton btnEdit = new JFXButton();
                    private final JFXButton btnDelete = new JFXButton();
                    private final HBox pane = new HBox(btnEdit, btnDelete);

                    {
                        pane.setSpacing(10);
                        pane.setAlignment(Pos.CENTER);
                        ImageView edit = new ImageView(iconEdit);
                        ImageView delete = new ImageView(iconDelete);
                        edit.setFitWidth(14);
                        edit.setFitHeight(14);
                        delete.setFitWidth(10);
                        delete.setFitHeight(10);

                        btnEdit.setGraphic(edit);
                        btnEdit.getStyleClass().add("btn--table");
                        btnEdit.setRipplerFill(Paint.valueOf("#26262b"));
                        btnDelete.setGraphic(delete);
                        btnDelete.getStyleClass().add("btn--table");
                        btnDelete.setRipplerFill(Paint.valueOf("#26262b"));

                        btnEdit.setOnAction(event -> {
                            Identificador item = getTableView().getItems().get(getIndex());
                            try {
                                handleEdit(item);
                            } catch (IOException e){
                                throw new RuntimeException(e);
                            }
                        });

                        btnDelete.setOnAction(event -> {
                            Identificador item = getTableView().getItems().get(getIndex());
                            handleDelete(item);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        });
        table.setItems(lista);
    }

    private void handleEdit(Identificador item) throws IOException {
        NavigationManager nm = new NavigationManager();
        VBox container = (VBox) tableContainer.getScene().getRoot().lookup("#container");

        FXMLLoader loader = null;
        Object controller = null;

        if (item instanceof  Cliente cliente) {
            loader =  new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("views/cadastro-cliente-page.fxml")));
            controller = new AlterarClienteController(cliente);
        } else if (item instanceof Animal animal) {
            loader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("views/cadastro-animal-page.fxml")));
            controller = new AlterarAnimalController(animal);
        } else if (item instanceof Raca raca) {
            loader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("views/cadastro-raca-page.fxml")));
            controller = new AlterarRacaController(raca);
        }

        if (loader != null) {
            loader.setController(controller);
            Parent root = loader.load();
            nm.navigateToPage(container, root);
        }
    }

    private void handleDelete(Identificador item) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Deseja inativar essa linha?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                try {
                    if (item instanceof Cliente) {
                        new ClienteDAO().deleteClient(item.getId());
                        reloadTable(this::loadClients);
                    } else if (item instanceof Animal) {
                        new AnimalDAO().deleteAnimal(item.getId());
                        reloadTable(this::loadAnimals);
                    } else {
                        new RacaDAO().deleteRaca(item.getId());
                        reloadTable(this::loadRaces);
                    }

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao excluir: " + e.getMessage());
                }
            }
        });
    }

    private void reloadTable(Runnable load) {
        table.getItems().clear();
        load.run();
    }

    public void handleSearch() {
        String q = searchField.getText().trim().toLowerCase();

        if (q.isEmpty()) {
            setTableData(listaObservavel);
            return;
        }

        ObservableList<Identificador> filtered = FXCollections.observableArrayList();
        String queryClean = q.replaceAll("\\D", "");
        for (Identificador item : listaObservavel) {
            if (item instanceof Cliente c && !c.getStatus()) continue;
            if (item instanceof Animal a && !a.getStatus()) continue;
            if (item instanceof Raca r && !r.getStatus()) continue;

            if (item instanceof Cliente cliente) {
                String nome = cliente.getNome().toLowerCase();
                String cpf = cliente.getCpf().replaceAll("\\D", "");

                if (nome.contains(q) || cpf.contains(q) || (!queryClean.isEmpty() && cliente.getCpf().contains(queryClean))) {
                    filtered.add(cliente);
                }
            }
            else if (item instanceof Raca raca) {
                String nome = raca.getNome().toLowerCase();
                String tipo = raca.getTipo().toLowerCase();
                if (nome.contains(q) || tipo.contains(q)) {
                    filtered.add(raca);
                }
            }
            else if (item instanceof Animal animal) {
                String nomeAnimal = animal.getNome() != null ? animal.getNome().toLowerCase() : "";
                String nomeRaca = animal.getRaca() != null && animal.getRaca().getNome() != null ? animal.getRaca().getNome().toLowerCase() : "";
                
                String cpfDonoAnimal = "";
                String nomeDonoAnimal = "";
                
                // Como agora o DAO faz o setCliente(), isso aqui não será mais nulo
                if (animal.getCliente() != null) {
                    // Removemos a máscara do CPF para a pesquisa funcionar com ou sem pontos
                    cpfDonoAnimal = animal.getCliente().getCpf() != null ? animal.getCliente().getCpf().replaceAll("\\D", "") : "";
                    nomeDonoAnimal = animal.getCliente().getNome() != null ? animal.getCliente().getNome().toLowerCase() : "";
                }

                if (nomeAnimal.contains(q) || nomeRaca.contains(q) || nomeDonoAnimal.contains(q) || 
                   (!queryClean.isEmpty() && cpfDonoAnimal.contains(queryClean))) {
                    filtered.add(animal);
                }
            }
        }

        table.setItems(filtered);

        if (filtered.isEmpty()) {
            if (!listaObservavel.isEmpty()) {
                if (listaObservavel.get(0) instanceof Cliente) {
                    table.setPlaceholder(new Label("Nenhum cliente encontrado para: '" + q + "'"));
                } else if (listaObservavel.get(0) instanceof Animal) {
                    table.setPlaceholder(new Label("Nenhum animal ou dono encontrado para: '" + q + "'"));
                } else {
                    table.setPlaceholder(new Label("Nenhuma raça encontrada para: '" + q + "'"));
                }
            }
        }
    }

    public void handleClearSearch() {
        searchField.setText("");
        setTableData(listaObservavel);
    }

}
