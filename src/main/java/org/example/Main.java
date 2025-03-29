package org.example;

import com.google.gson.Gson;
import lombok.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, GitAPIException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        Gson gson = new Gson();
        String requestBody = gson.toJson(new SampleRequestBody());

        HttpRequest request = HttpRequest.newBuilder(
                        new URI("https://postman-echo.com/post"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

//        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(gson.fromJson(response.body(), SampleResponseBody.class));

        File repoPath = new File("C:\\Users\\Bharath\\IdeaProjects\\RemoteAPICallsTest\\JgitCheck");

        Git git = Git.init().setDirectory(repoPath).call();
        System.out.println("Initialized repository at: " + git.getRepository().getDirectory());

        DiffEntry diff = git.diff().call().get(0);
        System.out.println(git.blame().call());
        System.out.println(diff);
        System.out.println(diff.getNewPath());
    }

}

class SampleRequestBody{
    Double a;
    String b;
    SampleRequestBody sampleRequestBody;
    static double count = 0;

    public SampleRequestBody() {
        this.a = count;
        count++;
        b = Double.toString(count);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class SampleResponseBody {
    Map<String, Object> args;
    Map<String, Object> data;
    Map <String, Object> headers;
}