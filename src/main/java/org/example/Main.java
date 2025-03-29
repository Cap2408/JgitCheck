package org.example;

import com.google.gson.Gson;
import lombok.*;
import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Map;

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

        File repoFile = new File("C:\\Users\\Bharath\\IdeaProjects\\RemoteAPICallsTest\\JgitCheck\\.git");

        Repository repository = new FileRepositoryBuilder()
                .setGitDir(repoFile)
                .readEnvironment()
                .findGitDir()
                .build();

        if (repository.resolve("HEAD") == null) {
            System.out.println("No HEAD found! Ensure the repository has at least one commit.");
            return;
        }

        Git git = new Git(repository);
        BlameCommand blameCommand = git.blame();
        blameCommand.setFilePath("src/main/java/org/example/Main.java");
        BlameResult result = blameCommand.call();

        if (result != null) {
            System.out.println("Blame info for: src/Main.java");
            for (int i = 0; i < result.getResultContents().size(); i++) {
                System.out.println("Line " + (i + 1) + ": " +
                        result.getSourceCommit(i).getAuthorIdent().getName());
            }
        } else {
            System.out.println("Blame result is null. Check file path or repo state.");
        }

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