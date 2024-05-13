package ui.demo.Server;

import ui.demo.Client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private ServerSocket serverSocket;
    private static Server server;
    private List<ClientHandler> clients = new ArrayList<>();

    private ServerController() {
        try {
            serverSocket = new ServerSocket(3001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerController getInstance() {
        return server != null ? server : (server = new Server());
    }

    public void start() {
        System.out.println("Server is running...");
        while (true) {
            try {
                // Chấp nhận kết nối từ client
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                // Tạo một client handler mới để xử lý kết nối từ client
                ClientHandler clientHandler = new ClientHandler(socket, this);
                // Thêm client handler vào danh sách các client
                clients.add(clientHandler);
                // Bắt đầu xử lý client
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức để gửi tin nhắn đến tất cả client
    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }

    // Phương thức để xóa một client handler khỏi danh sách khi client đó ngắt kết nối
    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Client disconnected: " + clientHandler.getClientSocket());
    }
}
