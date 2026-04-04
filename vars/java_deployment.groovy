node {
    // Define tool names as configured in 'Global Tool Configuration'
    def mvnHome = tool 'MAVEN_HOME' 

    try {
        // 1. Stage: Git Checkout
        // Pulls code from the repository defined in the Jenkins job configuration.
        stage('Checkout') {
            checkout scm
        }

        // 2. Stage: Build
        // Compiles the Java source code using Maven.
        stage('Build') {
            sh "'${mvnHome}/bin/mvn' clean compile"
        }

        // 3. Stage: Unit Test
        // Executes JUnit test cases and ensures the build fails if tests fail.
        stage('Test') {
            sh "'${mvnHome}/bin/mvn' test"
        }

        // 4. Stage: Code Quality (SonarQube)
        // Scans code for vulnerabilities and 'smells'. 
        // 'SonarQubeServer' must match your Jenkins System configuration name.
        stage('Code Quality') {
            withSonarQubeEnv('SonarQubeServer') {
                sh "'${mvnHome}/bin/mvn' sonar:sonar"
            }
        }

        // 5. Stage: Package
        // Generates the deployable artifact (JAR/WAR).
        stage('Package') {
            sh "'${mvnHome}/bin/mvn' package -DskipTests"
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        // 6. Post-Build Actions
        // Always archive test results and build artifacts regardless of success.
        junit '**/target/surefire-reports/*.xml'
        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }
}
