package de.sbs.fswi1.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sbs.fswi1.models.StudentDTO;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {

    public List<StudentDTO> findAll() {

        List<StudentDTO> students = new ArrayList<>();

        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/studenten")).
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
                    .uri(new URI("http://localhost:8080/studenten"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}