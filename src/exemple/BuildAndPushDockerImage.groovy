package example

def call(Map params) {
    def image = params.image
    sh "docker build -t ${image} ."
    sh "docker push ${image}"
}
