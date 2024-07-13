package de.sbs.fswi1.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sbs.fswi1.models.StudentDTO;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {

    private final String url;

    public DataAccessObject(String url) {
        this.url = url;
    }

    public List<StudentDTO> findAll() {
        List<StudentDTO> students = new ArrayList<>();
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url)).
                    GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                ObjectMapper mapper = new ObjectMapper();
                students = mapper.readValue(response.body(), new TypeReference<>() {
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public void save(StudentDTO student) {
        ObjectMapper mapper = new ObjectMapper();

        try (HttpClient client = HttpClient.newHttpClient()) {

            String requestBody = mapper.writeValueAsString(student);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/" + id))
                    .DELETE().build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(long id, StudentDTO student) {
        ObjectMapper mapper = new ObjectMapper();
        try (HttpClient client = HttpClient.newHttpClient()) {
            String requestBody = mapper.writeValueAsString(student);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}