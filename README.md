# Network Intrusion Detection System

Build System: Maven  
Java Version: 11 or higher

## How to Use It (Step by Step)

1. Make sure you have Java and Maven installed.
   - Run `java -version` and `mvn -v` to confirm.

2. Clone or download this project and open the folder.

3. Place the following files in the project root (same folder as pom.xml):
   - `ThreatSignatures.txt`
   - Your Firebase credentials file (example: `nids-firebase-firebase-adminsdk-3e2746bec3.json`)

4. Open a terminal in the project folder and compile the project:
   ```
   mvn compile
   ```

5. In one terminal, run the server:
   ```
   mvn exec:java '-Dexec.mainClass=com.nids.IDS_Server'

   ```

6. In another terminal, run the client:
   ```
   mvn exec:java '-Dexec.mainClass=com.nids.IDS_Client'
   
   ```

7. In the client terminal, type messages. If a message matches something in `ThreatSignatures.txt`, you will:
   - See an alert in the client
   - See a log in Firebase