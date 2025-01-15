// Questo controller gestisce le interazioni dell'utente con l'interfaccia grafica dell'applicazione ristorante.
// Include metodi per aggiungere clienti, cercare clienti, gestire ordini e visualizzare statistiche.
// Utilizza JavaFX per la creazione dell'interfaccia utente e per la gestione degli eventi.
// Le liste di clienti e ordini sono implementate come ObservableList per aggiornamenti automatici dell'interfaccia.
// Ogni metodo è annotato con @FXML per collegarlo agli elementi definiti nel file FXML.
// Questo controller è fondamentale per il funzionamento dell'applicazione e per garantire un'esperienza utente fluida.

package upo20052959.ristorante;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controller principale dell'applicazione ristorante.
 * Gestisce le interazioni dell'utente con l'interfaccia grafica.
 */
public class MyResturantController {

    /* campi IO dell'interfaccia */

    //gestione clienti
    @FXML
    private TextField clienteIdField;
    @FXML
    private TextField clienteAnnoField;
    @FXML
    private DatePicker clienteDataRegistrazione;
    @FXML
    private TableView<Cliente> clientiTable;
    @FXML
    private TableColumn<Cliente, String> idColumn;
    @FXML
    private TableColumn<Cliente, Integer> annoNascitaColumn;
    @FXML
    private TableColumn<Cliente, LocalDate> dataRegistrazioneColumn;
    @FXML
    private TextField searchIdField;
    @FXML
    private TextField etaMinField;
    @FXML
    private TextField etaMaxField;

    //gestione ordini
    @FXML
    private TextField ordineClienteIdField;
    @FXML
    private TextField numeroPiattiField;
    //menu a tendina : combobox
    @FXML
    private TextField tipoMenuField;
    //DatePicker calendario da cui selezionare la data
    @FXML
    private DatePicker dataOrdine;
    //tabella di visualizzazione della lista degli ordini
    @FXML
    private TableView<Ordine> ordiniTable;
    @FXML
    private TableColumn<Ordine, String> clienteIdOrdineColumn;//TODO ERR: non può essere inserito perchè l'oggetto ordine
    //non possiede l'attributo id
    @FXML
    private TableColumn<Ordine, Integer> numeroPiattiColumn;
    @FXML
    private TableColumn<Ordine, String> tipoMenuColumn;
    @FXML
    private TableColumn<Ordine, LocalDate> dataOrdineColumn;

    //statistiche
    @FXML
    private TextArea statisticheArea;
    @FXML
    private Label statusLabel;
    @FXML
    private TabPane tabPane;

    // Liste di clienti e ordini                                   (studiare che è sta roba)
    //forse utili per la oservation list ma toglierle per le operazioni comuni
    private ObservableList<Cliente> clientiList = FXCollections.observableArrayList();
    private ObservableList<Ordine> ordiniList = FXCollections.observableArrayList();

    /**
     * Inizializza il controller.
     * Configura le colonne delle tabelle e i campi dell'interfaccia utente.
     */
    @FXML
    public void initialize() {
        // Inizializza le colonne della tabella clienti
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        annoNascitaColumn.setCellValueFactory(new PropertyValueFactory<>("nascita"));
        dataRegistrazioneColumn.setCellValueFactory(new PropertyValueFactory<>("registrazione"));
        clientiTable.setItems(clientiList);

        // Inizializza le colonne della tabella ordini
        clienteIdOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        numeroPiattiColumn.setCellValueFactory(new PropertyValueFactory<>("numPiatti"));
        tipoMenuColumn.setCellValueFactory(new PropertyValueFactory<>("tipoMenu"));
        dataOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        ordiniTable.setItems(ordiniList);

        // Aggiungi effetti di fade-in all'avvio
        if (clientiTable != null) {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), clientiTable);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        }

        // Aggiungi animazione al cambio di tab
        if (tabPane != null) {
            tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                if (newTab != null && newTab.getContent() != null) {
                    Node content = newTab.getContent();
                    FadeTransition fade = new FadeTransition(Duration.millis(400), content);
                    fade.setFromValue(0.3);
                    fade.setToValue(1.0);
                    fade.play();
                }
            });
        }

        // Aggiungi effetto di shake quando c'è un errore
        if (statusLabel != null) {
            statusLabel.textProperty().addListener((obs, oldText, newText) -> {
                if (newText != null && newText.startsWith("Errore")) {
                    TranslateTransition shake = new TranslateTransition(Duration.millis(100), statusLabel);
                    shake.setFromX(0);
                    shake.setByX(10);
                    shake.setCycleCount(4);
                    shake.setAutoReverse(true);
                    shake.play();
                }
            });
        }
    }

    /**
     * Aggiunge un nuovo cliente alla lista.
     */
    @FXML
    protected void handleAddCliente() {
        try {
            String id = clienteIdField.getText().trim();
            int anno = Integer.parseInt(clienteAnnoField.getText().trim());
            LocalDate registrazione = clienteDataRegistrazione.getValue();

            if (registrazione == null) {
                showError("Seleziona una data di registrazione");
                return;
            }
            //aaaaaaaaaaaaaaa veniva usato il costruttore della classe cliente e non qullo del della classe Mioristorante
            Cliente cliente;
            // Se l'ID è vuoto, generiamo un ID automatico
            if (id.isEmpty()) {
                cliente = new Cliente(anno, registrazione);
            } else {
                // Se l'ID è specificato, lo usiamo
                cliente = new Cliente(id, anno, registrazione);
            }

            // Aggiungi il cliente sia alla lista locale che a quella statica
            clientiList.add(cliente);
            MioRistorante.addClienteCM(cliente.getId(), cliente.getNascita(), cliente.getRegistrazione());
            clearClienteFields();
            showStatus("Cliente aggiunto con successo");
        } catch (NumberFormatException e) {
            showError("Anno di nascita non valido");
        } catch (IdAlreadyUsed e) {
            showError("ID già in uso");
        } catch (Exception e) {
            showError("Errore nell'aggiunta del cliente: " + e.getMessage());
        }
    }

    /**
     * Cerca un cliente per ID.
     */
    @FXML
    protected void handleSearchById() {
        String id = searchIdField.getText().trim();
        if (id.isEmpty()) {
            showError("Inserisci un ID da cercare");
            return;
        }

        Cliente cliente = MioRistorante.findClienteCM(id);
        if (cliente != null) {
            clientiTable.getItems().clear();
            showStatus("Cliente trovato: " + cliente.toString());
        } else {
            showError("Cliente non trovato");
        }
    }

    /**
     * Cerca clienti per fascia di età.
     */
    @FXML
    protected void handleSearchByEta() {
        try {
            int min = Integer.parseInt(etaMinField.getText().trim());
            int max = Integer.parseInt(etaMaxField.getText().trim());

            int annoCorrente = LocalDate.now().getYear();
            clientiTable.getItems().clear();
            //TODO: esiste già questa funzione
            for (Cliente c : clientiList) {
                int eta = annoCorrente - c.getNascita();
                if (eta >= min && eta <= max) {
                    clientiTable.getItems().add(c);
                }
            }

            if (clientiTable.getItems().isEmpty()) {
                showError("Nessun cliente trovato in questo range di età");
            }
        } catch (NumberFormatException e) {
            showError("Inserisci età valide");
        }
    }

    /**
     * Aggiunge un nuovo ordine alla lista.
     */
    @FXML
    protected void handleAddOrdine() {
        try {
            String clienteId = ordineClienteIdField.getText().trim();
            int numPiatti = Integer.parseInt(numeroPiattiField.getText().trim());
            String tipoMenu = tipoMenuField.getText().trim();
            LocalDate data = dataOrdine.getValue();

            if (clienteId.isEmpty() || tipoMenu.isEmpty() || data == null) {
                showError("Compila tutti i campi");
                return;
            }

            //TODO:esiste già questa funzione, usare quella già esistente
            Cliente cliente = null;
            // if (MioRistorante.findClienteCM(clienteId) != null) cliente = MioRistorante.findClienteCM(clienteId);
            for (Cliente c : clientiList) {
                if (c.getId().equals(clienteId)) {
                    cliente = c;
                    break;
                }
            }

            if (cliente == null) {
                showError("Cliente non trovato");
                return;
            }

            Ordine ordine = new Ordine(numPiatti, tipoMenu, data);
            cliente.getOrdini().add(ordine);
            ordiniList.add(ordine);
            clearOrdineFields();
            showStatus("Ordine aggiunto con successo");
        } catch (NumberFormatException e) {
            showError("Numero piatti non valido");
        } catch (Exception e) {
            showError("Errore nell'aggiunta dell'ordine: " + e.getMessage());
        }
    }

    /**
     * Visualizza le statistiche sui piatti.
     */
    @FXML
    protected void handleStatistichePiatti() {
        StringBuilder stats = new StringBuilder("Statistiche Numero Piatti:\n\n");
        for (Cliente c : clientiList) {
            stats.append("Cliente ").append(c.getId()).append(":\n");
            ArrayList<Integer> numPiatti = c.getListNumPiatti();
            for (Integer num : numPiatti) {
                stats.append("- ").append(num).append(" piatti\n");
            }
            stats.append("\n");
        }
        statisticheArea.setText(stats.toString());
    }

    /**
     * Visualizza le statistiche sui menu.
     */
    @FXML
    protected void handleStatisticheMenu() {
        StringBuilder stats = new StringBuilder("Statistiche Tipo Menu:\n\n");
        for (Cliente c : clientiList) {
            stats.append("Cliente ").append(c.getId()).append(":\n");
            ArrayList<String> tipiMenu = c.getListTipoMenu();
            for (String tipo : tipiMenu) {
                stats.append("- Menu ").append(tipo).append("\n");
            }
            stats.append("\n");
        }
        statisticheArea.setText(stats.toString());
    }

    /**
     * Chiude l'applicazione.
     */
    @FXML
    protected void handleExit() {
        Stage stage = (Stage) clientiTable.getScene().getWindow();
        stage.close();
    }

    /**
     * Pulisce i campi del cliente.
     */
    private void clearClienteFields() {
        clienteIdField.clear();
        clienteAnnoField.clear();
        clienteDataRegistrazione.setValue(null);
    }

    /**
     * Pulisce i campi dell'ordine.
     */
    private void clearOrdineFields() {
        ordineClienteIdField.clear();
        numeroPiattiField.clear();
        tipoMenuField.clear();
        dataOrdine.setValue(null);
    }

    /**
     * Visualizza un messaggio di errore.
     *
     * @param message il messaggio di errore
     */
    private void showError(String message) {
        statusLabel.setText("Errore: " + message);
    }

    /**
     * Visualizza un messaggio di stato.
     *
     * @param message il messaggio di stato
     */
    private void showStatus(String message) {
        statusLabel.setText(message);
    }
}
