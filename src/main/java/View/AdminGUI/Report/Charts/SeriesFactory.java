package View.AdminGUI.Report.Charts;

import AdminModel.Report.Children.Model.ResponseMonthlyPovertyRate;
import AdminModel.Report.Children.Model.ResponsePovertyFactor;
import AdminModel.Report.Children.Model.ResponsePovertyRate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import utility.Logger;

import java.util.ArrayList;

/**
 * Created by Didoy on 2/24/2016.
 */
public class SeriesFactory {

    int x = 0;

    private SeriesListener seriesListener;
    public XYChart.Series createSeries(String seriesName, String xValue, int yValue  ){

        XYChart.Series series = new XYChart.Series();

        series.setName(seriesName);

        XYChart.Data data =  new XYChart.Data<String, Number>(xValue, yValue);

        data.nodeProperty().addListener(new ChangeListener<Node>() {
            @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                if (node != null) {
                    displayLabelForData(data);
                }
            }
        });

        series.getData().add(data);

        return series;

    }

    public XYChart.Series createPovertyFactorSeriesByList(String seriesName, ArrayList povertyFactorList   ){

        ArrayList factorList =   povertyFactorList;

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);


        while (x <= factorList.size() -1 ){

            ResponsePovertyFactor factors = (ResponsePovertyFactor)  factorList.get(x);

            XYChart.Data data1  =  new XYChart.Data("Unemployed", factors.getUnemployed());
            XYChart.Data data2 =  new XYChart.Data("UnderEmployed", factors.getUnderemployed());
            XYChart.Data data3 =  new XYChart.Data("No other Income", factors.getNoOtherIncome());
            XYChart.Data data4 =  new XYChart.Data("Below City Threshold", factors.getBelowMinimun());
            XYChart.Data data5  =  new XYChart.Data("Illegal Settlers ", factors.getNoShelter());


            data1.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data1);
                    }
                }
            });
            data2.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data2);
                    }
                }
            });
            data3.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data3);
                    }
                }
            });
            data4.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data4);
                    }
                }
            });
            data5.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data5);
                    }
                }
            });

            series.getData().addAll(data1, data2, data3, data4, data5);

            x++;
        }
        x = 0;


        return series;
    }

    public XYChart.Series createPovertyFactorSeriesByData(String seriesName, ResponsePovertyFactor povertyFactor   ){

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);

            XYChart.Data data1  =  new XYChart.Data("Unemployed", povertyFactor.getUnemployed());
            XYChart.Data data2 =  new XYChart.Data("UnderEmployed", povertyFactor.getUnderemployed());
            XYChart.Data data3 =  new XYChart.Data("No other Income", povertyFactor.getNoOtherIncome());
            XYChart.Data data4 =  new XYChart.Data("Below City Threshold", povertyFactor.getBelowMinimun());
            XYChart.Data data5  =  new XYChart.Data("Illegal Settlers ", povertyFactor.getNoShelter());


            data1.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data1);
                    }
                }
            });
            data2.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data2);
                    }
                }
            });
            data3.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data3);
                    }
                }
            });
            data4.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data4);
                    }
                }
            });
            data5.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data5);
                    }
                }
            });

            series.getData().addAll(data1, data2, data3, data4, data5);


        return series;
    }


    public XYChart.Series createPovertyPopulationSeries(String seriesName, ArrayList povertyFactorList  ){

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);

        int x = 0;

        ArrayList povertyList =   povertyFactorList;

        while (x <= povertyList.size() - 1){
            ResponsePovertyRate povertyRate = (ResponsePovertyRate) povertyList.get(x);

            final  XYChart.Data data =  new XYChart.Data(povertyRate.getBarangayName(), povertyRate.getUnresolvePopulation());

            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data);
                    }
                }
            });


            series.getData().add(data);

            x++;
        }


        return series;
    }


    public XYChart.Series createPovertySeriesSpecificOverView(String seriesName, ArrayList povertyPopulationList   ){

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);

        int x = 0;

        ArrayList povertyList =   povertyPopulationList;

        while (x <= povertyList.size() - 1){
            ResponseMonthlyPovertyRate monthlyPovertyRate = (ResponseMonthlyPovertyRate) povertyList.get(x);

            final  XYChart.Data data =  new XYChart.Data(monthlyPovertyRate.getMonth(), monthlyPovertyRate.getPopulation());

            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data);
                    }
                }
            });


            series.getData().add(data);

            x++;
        }

        return series;
    }



    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");

        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }

    public void addHoverToSeries(XYChart.Series series, String date, String barangay){
        ObservableList<XYChart.Data> listData = series.getData();

        for (XYChart.Data data : listData){
            Node node = data.getNode();
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String value = (String) data.getXValue();

                       seriesListener.provideData(value, date, barangay);

                }
            });

            node.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setScaleX(node.getScaleX() + .5);
                    node.setScaleY(node.getScaleY() + .5);
                }
            });
            node.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setScaleX(node.getScaleX() - .5);
                    node.setScaleY(node.getScaleY() - .5);
                }
            });

        };
    }

    public void addSeriesFactoryListener(SeriesListener seriesListener){
        this.seriesListener = seriesListener;
    }

}
