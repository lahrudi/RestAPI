import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return a post")
    request {
        method GET()
        url "/posts/1"
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                userId: 1,
                id: 1,
                title: "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body: "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
        )
    }
}