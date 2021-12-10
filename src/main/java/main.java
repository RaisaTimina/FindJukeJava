import static spark.Spark.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.JsonParser;

public class main {
  public static void main(String[] args) {

      //to run ex: http://localhost:4567/compare/358a044e-decc-47cc-aaf1-e2253a00998e

    get(
        "/compare/:id",
        (req, res) -> {

          // jukes
          HttpClient client = HttpClient.newHttpClient();
          HttpRequest jukeRequest =
              HttpRequest.newBuilder()
                  .uri(
                      URI.create(
                          "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes"))
                  .build();
          HttpResponse<String> jukeResponse =
              client.send(jukeRequest, HttpResponse.BodyHandlers.ofString());

          // settings
          HttpClient settingsClient = HttpClient.newHttpClient();
          HttpRequest settingsRequest =
              HttpRequest.newBuilder()
                  .uri(
                      URI.create(
                          "http://my-json-server.typicode.com/touchtunes/tech-assignment/settings"))
                  .build();

          HttpResponse<String> settingsResponse =
              settingsClient.send(settingsRequest, HttpResponse.BodyHandlers.ofString());

          //variable declaration
          var offset  = req.params(":offset");
          var limit = req.params(":limit");
          int i = 0;
          int j = 0;
          var settingsSize =
              JsonParser.parseString(settingsResponse.body())
                  .getAsJsonObject()
                  .get("settings")
                  .getAsJsonArray()
                  .size();
          var settingsArray =
              JsonParser.parseString(settingsResponse.body())
                  .getAsJsonObject()
                  .get("settings")
                  .getAsJsonArray();
          var settReqAll = settingsArray.get(i).getAsJsonObject().get("requires");
          var settReqSize = settReqAll.getAsJsonArray().size();
          var settId = settingsArray.get(i).getAsJsonObject().get("id");
          ArrayList<String> requirmentsList = new ArrayList<String>();
          ;
          String settIdToFind;
          var settRequirment = settReqAll.getAsJsonArray().get(j).getAsString();

          var jukeSize = JsonParser.parseString(jukeResponse.body()).getAsJsonArray().size();
          var jsonJuke = JsonParser.parseString(jukeResponse.body()).getAsJsonArray();
          var getComponents = jsonJuke.get(i).getAsJsonObject().get("components");
          var getId = jsonJuke.get(i).getAsJsonObject().get("id");
          var getComponentsSize = getComponents.getAsJsonArray().size();
          var getComponent =
              getComponents.getAsJsonArray().get(j).getAsJsonObject().get("name").getAsString();
          ArrayList<String> componentsList = new ArrayList<String>();
          ArrayList<String> checkedList = new ArrayList<String>();
          ArrayList<String> jukeIdList = new ArrayList<String>();

          //search team
          for (i = 0; i < settingsSize; i++) {

            settReqAll = settingsArray.get(i).getAsJsonObject().get("requires");
            settReqSize = settReqAll.getAsJsonArray().size();
            settId = settingsArray.get(i).getAsJsonObject().get("id");

            settIdToFind = req.params(":id"); // settIdToFind = "2321763c-8e06-4a31-873d-0b5dac2436da";

              // fetching requirements to search by according to specified settings id
            if (settId.getAsString().equals(settIdToFind)) {
              for (j = 0; j < settReqSize; j++) {
                settRequirment = settReqAll.getAsJsonArray().get(j).getAsString();
                requirmentsList.add(settRequirment);
              }
            }
          }

          //looping through juke components
          for (i = 0; i < jukeSize; i++) {
            getId = jsonJuke.get(i).getAsJsonObject().get("id");
            componentsList = new ArrayList<String>();
            getComponents = jsonJuke.get(i).getAsJsonObject().get("components");
            getComponentsSize = getComponents.getAsJsonArray().size();

            checkedList = new ArrayList<String>();
            //            jukeIdList = new ArrayList<String>();

            if (requirmentsList.isEmpty()) {
              System.out.println(getId.toString());
              System.out.println("found");
              System.out.println();
              jukeIdList.add(getId.toString());
            }

            for (j = 0; j < getComponentsSize; j++) {
              getComponent =
                  getComponents.getAsJsonArray().get(j).getAsJsonObject().get("name").getAsString();
              componentsList.add(getComponent);

              //saving matching jukes id in checkedList
              for (int y = 0; y < requirmentsList.size(); y++) {
                if (getComponent.contains(requirmentsList.get(y))) {
                  checkedList.add(requirmentsList.get(y));
                  Collections.sort(checkedList);
                  Collections.sort(requirmentsList);

                  if (checkedList.equals(requirmentsList)) {
                    System.out.println(getId.toString());
                    System.out.println("found");
                    System.out.println(requirmentsList);
                    System.out.println();
                    jukeIdList.add(getId.toString());
                  }
                }
              }
            }
          }
          return jukeIdList.toString();
        });

    // to run paginated list ex: http://localhost:4567/compare/358a044e-decc-47cc-aaf1-e2253a00998e/1/10
    get(
        "/compare/:id/:offset/:limit",
        (req, res) -> {

          // jukes
          HttpClient client = HttpClient.newHttpClient();
          HttpRequest jukeRequest =
              HttpRequest.newBuilder()
                  .uri(
                      URI.create(
                          "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes"))
                  .build();
          HttpResponse<String> jukeResponse =
              client.send(jukeRequest, HttpResponse.BodyHandlers.ofString());

          // settings
          HttpClient settingsClient = HttpClient.newHttpClient();
          HttpRequest settingsRequest =
              HttpRequest.newBuilder()
                  .uri(
                      URI.create(
                          "http://my-json-server.typicode.com/touchtunes/tech-assignment/settings"))
                  .build();

          HttpResponse<String> settingsResponse =
              settingsClient.send(settingsRequest, HttpResponse.BodyHandlers.ofString());

            //variable declaration
          var offset = req.params(":offset");
          var limit = req.params(":limit");
          int i = 0;
          int j = 0;
          var settingsSize =
              JsonParser.parseString(settingsResponse.body())
                  .getAsJsonObject()
                  .get("settings")
                  .getAsJsonArray()
                  .size();
          var settingsArray =
              JsonParser.parseString(settingsResponse.body())
                  .getAsJsonObject()
                  .get("settings")
                  .getAsJsonArray();
          var settReqAll = settingsArray.get(i).getAsJsonObject().get("requires");
          var settReqSize = settReqAll.getAsJsonArray().size();
          var settId = settingsArray.get(i).getAsJsonObject().get("id");
          ArrayList<String> requirmentsList = new ArrayList<String>();
          ;
          String settIdToFind;
          var settRequirment = settReqAll.getAsJsonArray().get(j).getAsString();

          var jukeSize = JsonParser.parseString(jukeResponse.body()).getAsJsonArray().size();
          var jsonJuke = JsonParser.parseString(jukeResponse.body()).getAsJsonArray();
          var getComponents = jsonJuke.get(i).getAsJsonObject().get("components");
          var getId = jsonJuke.get(i).getAsJsonObject().get("id");
          var getComponentsSize = getComponents.getAsJsonArray().size();
          var getComponent =
              getComponents.getAsJsonArray().get(j).getAsJsonObject().get("name").getAsString();
          ArrayList<String> componentsList = new ArrayList<String>();
          ArrayList<String> checkedList = new ArrayList<String>();
          ArrayList<String> jukeIdList = new ArrayList<String>();

          for (i = 0; i < settingsSize; i++) {

            settReqAll = settingsArray.get(i).getAsJsonObject().get("requires");
            settReqSize = settReqAll.getAsJsonArray().size();
            settId = settingsArray.get(i).getAsJsonObject().get("id");

            // settIdToFind = "2321763c-8e06-4a31-873d-0b5dac2436da";
            settIdToFind = req.params(":id");

            if (settId.getAsString().equals(settIdToFind)) {
              for (j = 0; j < settReqSize; j++) {
                settRequirment = settReqAll.getAsJsonArray().get(j).getAsString();
                requirmentsList.add(settRequirment);
              }
            }
          }

          for (i = 0; i < jukeSize; i++) {
            getId = jsonJuke.get(i).getAsJsonObject().get("id");
            componentsList = new ArrayList<String>();
            getComponents = jsonJuke.get(i).getAsJsonObject().get("components");
            getComponentsSize = getComponents.getAsJsonArray().size();

            checkedList = new ArrayList<String>();

            if (requirmentsList.isEmpty()) {
              jukeIdList.add(getId.toString());
            }

            for (j = 0; j < getComponentsSize; j++) {
              getComponent =
                  getComponents.getAsJsonArray().get(j).getAsJsonObject().get("name").getAsString();
              componentsList.add(getComponent);

              for (int y = 0; y < requirmentsList.size(); y++) {
                if (getComponent.contains(requirmentsList.get(y))) {
                  checkedList.add(requirmentsList.get(y));
                  Collections.sort(checkedList);
                  Collections.sort(requirmentsList);

                  if (checkedList.equals(requirmentsList)) {
                    System.out.println(getId.toString());
                    jukeIdList.add(getId.toString());
                  }
                }
              }
            }
          }

          System.out.println("====");

          System.out.println(Integer.parseInt(offset));
          System.out.println(Integer.parseInt(limit));
          ArrayList<String> jukeIdListPaginated = new ArrayList<String>();
          for (int o = Integer.parseInt(offset); o < (Integer.parseInt(limit) + Integer.parseInt(offset)); o++) {
            jukeIdListPaginated.add(jukeIdList.get(o));
          }
          System.out.println(jukeIdListPaginated);

          return jukeIdListPaginated.toString();
        });
  }
}
