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
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Controller principale dell'applicazione ristorante.
 * Gestisce le interazioni dell'utente con l'interfaccia grafica.
 */
public class MyResturantController {

    /* campi IO dell'interfaccia */

    //gestione clienti
    @FXML private TextField clienteAnnoField;
    @FXML private TextField clienteIdField;
    @FXML private DatePicker clienteDataRegistrazione;

    /*tabelle*/
    @FXML private TableView<Cliente> clientiTable;

    @FXML private TableColumn<Cliente, String> idColumn;
    @FXML private TableColumn<Cliente, Integer> annoNascitaColumn;
    @FXML private TableColumn<Cliente, LocalDate> dataRegistrazioneColumn;

    /*ricerche*/
    @FXML private TextField searchIdField;
    @FXML private TextField etaMinField;
    @FXML private TextField etaMaxField;

    //gestione ordini
    @FXML private TextField ordineClienteIdField;
    @FXML private TextField numeroPiattiField;
    @FXML private ComboBox<String> tipoMenuField;
    //DatePicker calendario da cui selezionare la data
    @FXML private DatePicker dataOrdine;
    //tabella di visualizzazione della lista degli ordini
    @FXML private TableView<Ordine> ordiniTable;
    //non possiede l'attributo id
    @FXML private TableColumn<Ordine, Integer> numeroPiattiColumn;
    @FXML private TableColumn<Ordine, String> tipoMenuColumn;
    @FXML private TableColumn<Ordine, LocalDate> dataOrdineColumn;



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
    // Inizializza le stringhe al capo delle colonne per nominarle

        // Inizializza le colonne della tabella clienti
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        annoNascitaColumn.setCellValueFactory(new PropertyValueFactory<>("nascita"));
        dataRegistrazioneColumn.setCellValueFactory(new PropertyValueFactory<>("registrazione"));
        clientiTable.setItems(clientiList);

        // Inizializza le colonne della tabella ordini
        numeroPiattiColumn.setCellValueFactory(new PropertyValueFactory<>("numPiatti"));
        tipoMenuColumn.setCellValueFactory(new PropertyValueFactory<>("tipoMenu"));
        dataOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        ordiniTable.setItems(ordiniList);

        //inizializza input che necessitano dell'inizializzazione
        // Inizializza il ComboBox per il tipo di menu con tutti i valori dell'enum
        tipoMenuField.getItems().addAll(
            TipoMenu.CARTA.toString(),
            TipoMenu.CARNE.toString(),
            TipoMenu.GLUTEN.toString(),
            TipoMenu.PESCE.toString(),
            TipoMenu.VEGANO.toString(),
            TipoMenu.VEGETARIANO.toString()
        );
        tipoMenuField.setPromptText("Tipo Menu");



    //effetti visivi
        // Aggiungi effetti di fade-in all'avvio
        if (clientiTable != null) {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), clientiTable);
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







//associacioni del model al view atravverso il controller

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
            try {
                MioRistorante.addClienteCM(id, anno, registrazione);
                showStatus("Cliente aggiunto con successo");
                clearClienteFields();
            }catch (ClienteNonAggiunto e){
                showError(e.getMessage());
            }

            clientiList = FXCollections.observableArrayList(MioRistorante.getClienti());
            clientiTable.setItems(clientiList);



        } catch (NumberFormatException e) {
            showError("Anno di nascita non valido");
        } catch (IdAlreadyUsed e) {
            showError("ID già in uso");
        } catch (Exception e) {
            //showError("Errore nell'aggiunta del cliente: " + e.getMessage());
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
            ObservableList<Cliente> tmp = FXCollections.observableArrayList();
            tmp.add(cliente);
            clientiTable.setItems(tmp);
            showStatus("Cliente trovato");
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

            clientiTable.getItems().clear();



            LinkedList<Cliente> sas = new LinkedList<>(MioRistorante.findClienteEtaCM(min, max)) ;
            ObservableList ricerca = FXCollections.observableArrayList(sas);
            clientiTable.setItems(ricerca);


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
            String tipoMenuStr = tipoMenuField.getValue();
            LocalDate data = dataOrdine.getValue();

            if (clienteId.isEmpty() || tipoMenuStr == null || data == null) {
                showError("Compila tutti i campi");
                return;
            }


            TipoMenu tipoMenu = null;
            for (TipoMenu tipo : TipoMenu.values()) {
                if (tipo.toString().equals(tipoMenuStr)) {
                    tipoMenu = tipo;
                    break;
                }
            }

            if (tipoMenu == null) {
                showError("Tipo menu non valido");
                return;
            }

            // Verifica che il cliente esista
            Cliente cliente = MioRistorante.findClienteCM(clienteId);
            if (cliente == null) {
                showError("Cliente non trovato");
                return;
            }

            Ordine ordine = new Ordine(numPiatti, tipoMenu, data);

            ordiniList.add(ordine);
            clearOrdineFields();
            showStatus("Ordine aggiunto con successo");
        } catch (NumberFormatException e) {
            showError("Numero di piatti non valido");
        } catch (Exception e) {
            showError("Errore nell'aggiunta dell'ordine: " + e.getMessage());
        }
    }



    //usare le funzioni del model
    //statistiche
    @FXML private TextArea statisticheArea;
    @FXML private Label statusLabel;
    @FXML private TabPane tabPane;
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
    protected void statisticheTipoMenuClasse() {
        StringBuilder stats = new StringBuilder("Statistiche Tipo Menu:\n\n");
        
        // Crea un contatore per ogni tipo di menu
        Map<TipoMenu, Integer> conteggio = new EnumMap<>(TipoMenu.class);
        for (TipoMenu tipo : TipoMenu.values()) {
            conteggio.put(tipo, 0);
        }
        
        // Conta le occorrenze di ogni tipo di menu
        for (Ordine ordine : ordiniList) {
            TipoMenu tipo = ordine.getTipoMenu();
            conteggio.put(tipo, conteggio.get(tipo) + 1);
        }
        
        // Genera le statistiche
        for (Map.Entry<TipoMenu, Integer> entry : conteggio.entrySet()) {
            if (entry.getValue() > 0) {
                stats.append("Menu ").append(entry.getKey()).append(": ")
                     .append(entry.getValue()).append(" ordini\n");
            }
        }
        
        if (ordiniList.isEmpty()) {
            stats.append("Nessun ordine presente nel sistema.");
        }
        
        statisticheArea.setText(stats.toString());
    }

    /**
     * Ricarica la tabella clienti con la lista completa
     */
    @FXML
    protected void handleRefreshTable() {
        clientiList = FXCollections.observableArrayList(MioRistorante.getClienti());
        clientiTable.setItems(clientiList);
        showStatus("Lista clienti aggiornata");
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
        tipoMenuField.setValue(null);
        dataOrdine.setValue(null);
    }

    /**
     * Visualizza un messaggio di errore per 4 secondi con dissolvenza.
     *
     * @param message il messaggio di errore
     */
    private void showError(String message) {
        statusLabel.setText("Errore: " + message);
        // Crea una transizione di dissolvenza
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), statusLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(4)); // Attende 4 secondi prima della dissolvenza
        fadeOut.play();
        
        // Ripristina l'opacità per il prossimo messaggio
        fadeOut.setOnFinished(event -> {
            statusLabel.setOpacity(1.0);
            statusLabel.setText("");
        });
    }

    /**
     * Visualizza un messaggio di stato per 4 secondi con dissolvenza.
     *
     * @param message il messaggio di stato
     */
    private void showStatus(String message) {
        statusLabel.setText(message);
        // Crea una transizione di dissolvenza
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), statusLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(4)); // Attende 4 secondi prima della dissolvenza
        fadeOut.play();
        
        // Ripristina l'opacità per il prossimo messaggio
        fadeOut.setOnFinished(event -> {
            statusLabel.setOpacity(1.0);
            statusLabel.setText("");
        });
    }
}
