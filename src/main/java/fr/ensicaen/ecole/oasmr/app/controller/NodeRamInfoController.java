package fr.ensicaen.ecole.oasmr.app.controller;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeRamInfoController extends View {

    @FXML
    VBox ramVBox;

    private Tile ramGraph;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;
    private Tile cpuGraph;

    public NodeRamInfoController(View parent) throws IOException {
        super("NodeRamInfo", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
        ramGraph = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .title("RAM Usage")
                .animated(true)
                .textVisible(false)
                .averagingPeriod(25)
                .barColor(Tile.YELLOW_ORANGE)
                .barBackgroundColor(Color.rgb(255, 0, 0, 0.1))
                .sections(new eu.hansolo.tilesfx.Section(0, 33, Tile.GREEN),
                        new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                        new eu.hansolo.tilesfx.Section(67, 100, Tile.RED))
                .sectionsVisible(true)
                .highlightSections(true)
                .strokeWithGradient(true)
                .gradientStops(new Stop(0.0, Tile.GREEN),
                        new Stop(0.33, Tile.GREEN),
                        new Stop(0.33,Tile.YELLOW),
                        new Stop(0.67, Tile.YELLOW),
                        new Stop(0.67, Tile.RED),
                        new Stop(1.0, Tile.RED))
                .prefSize(150,150)
                .build();
        ramVBox.getChildren().add(ramGraph);
    }

    @Override
    protected void onStart() {
        if(requestManager == null){
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        //TODO : Configure Graph and update with heartbeat
    }

    @Override
    public void onStop() {

    }
}
