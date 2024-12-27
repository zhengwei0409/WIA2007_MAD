package com.example.madpart1;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChatroomActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton back_btn;
    ImageButton sendButton;
    EditText messageEditText;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    ChatAI chatAI;
    List<String> symptoms;
    List<String> gotSymptoms;
    private String conclusionMessage;
    private boolean isSelectingSymptoms = false;
    private boolean isAskingDuration = false;
    private boolean isAwaitingNextAction = false;
    private List<String> symptomList = new ArrayList<>();
    private List<String> selectedSymptoms = new ArrayList<>();
    private final int MESSAGE_DELAY = 1000;  // Delay time in milliseconds (1 second)
    private Handler messageHandler = new Handler();  // Handler for delayed message posting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        recyclerView = findViewById(R.id.recycler_view);
        back_btn = findViewById(R.id.back_btn);
        sendButton = findViewById(R.id.send_btn);
        messageEditText = findViewById(R.id.message_edit_text);

        gotSymptoms = new ArrayList<>();
        symptoms = new ArrayList<>();
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(ChatroomActivity.this, HomePage.class);
            startActivity(intent);
        });

        addDelayedResponse("Welcome to the MedConnect Chatroom.");
        addDelayedResponse("Please input an valid option, and I'll try to find a right way for you.");
        addDelayedResponse("1. Symptoms \n2. Appointment Details \n3. Chat with AI");

        readSymptomsFromFile();

        sendButton.setOnClickListener(v -> {
            String input = messageEditText.getText().toString().trim();
            if (!input.isEmpty()) {
                addToChat(input, Message.SENT_BY_ME);
                handleUserInput(input);
                messageEditText.setText("");
            }
        });
    }

    // Method to add a delayed response
    private void addDelayedResponse(String response) {
        messageHandler.postDelayed(() -> addResponse(response), MESSAGE_DELAY);
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        addToChat(response, Message.SENT_BY_BOT);
    }

    // Method to read symptoms from the file
    void readSymptomsFromFile() {
        try {
            AssetManager assetManager = getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("symptoms.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                symptomList.add(line); // Add each symptom to the list
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading symptoms file", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle the user's input
    private void handleUserInput(String userInput) {
        if (isSelectingSymptoms) {
            processSymptomSelection(userInput);
        } else if (isAskingDuration) {
            processDurationResponse(userInput);
        } else if (isAwaitingNextAction) {
            handleNextAction(userInput);
        } else {
            switch (userInput) {
                case "1": // Symptoms
                    addResponse("You selected: Symptoms.");
                    messageHandler.postDelayed(() -> showSymptomOptions(), MESSAGE_DELAY);  // Delay symptom options
                    isSelectingSymptoms = true;
                    break;
                case "2": // Appointment Details
                    addDelayedResponse("Redirecting you to Appointment Details...");
                    messageHandler.postDelayed(() -> {
                        Intent intent = new Intent(ChatroomActivity.this, AppointmentPage.class);
                        startActivity(intent);
                    }, MESSAGE_DELAY);
                    break;
                case "3": // Chat with AI
                    Intent intent = new Intent(ChatroomActivity.this, ChatAI.class);
                    startActivity(intent);
                    break;
                default:
                    addDelayedResponse("Invalid option. Please choose a valid number (1-4).");
                    break;
            }
        }
    }

    // Method to show the symptoms dynamically as options
    private void showSymptomOptions() {
        StringBuilder symptomOptions = new StringBuilder("Please select symptoms by their numbers (e.g., 1,2,3):\n");

        // Add each symptom with a number in front
        for (int i = 0; i < symptomList.size(); i++) {
            symptomOptions.append(i + 1).append(". ").append(symptomList.get(i)).append("\n");
        }

        // Display the symptoms as options
        addResponse(symptomOptions.toString());
    }

    // Method to process the symptom selection
    private void processSymptomSelection(String userInput) {
        String[] selectedNumbers = userInput.split(",");  // Split input by commas
        selectedSymptoms.clear();  // Clear previous selections

        for (String number : selectedNumbers) {
            try {
                int index = Integer.parseInt(number.trim()) - 1;  // Convert to 0-based index
                if (index >= 0 && index < symptomList.size()) {
                    selectedSymptoms.add(symptomList.get(index));  // Add the valid symptom
                } else {
                    addResponse("Invalid selection: " + number);
                }
            } catch (NumberFormatException e) {
                addResponse("Invalid input: " + number);
            }
        }

        if (!selectedSymptoms.isEmpty()) {
            String selectedMessage = "You've selected: " + String.join(", ", selectedSymptoms) +
                    ". How long have you been experiencing them?";
            addResponse(selectedMessage);

            isSelectingSymptoms = false;
            isAskingDuration = true;
        } else {
            addResponse("No valid symptoms selected. Please try again.");
        }
    }

    // Method to process the duration response
    private void processDurationResponse(String userInput) {
        // Validate and handle the duration input
        conclusionMessage = " been experiencing " + selectedSymptoms + " for " + userInput + ".";
        addResponse("Got it. You've" + conclusionMessage);
        // Provide next actions or recommendations
        addResponse("Would you like to: \n1. Schedule an appointment\n2. Get general advice from MedConnect AI");
        isAwaitingNextAction = true;
        isAskingDuration = false;
    }

    // Handle next actions after duration input
    private void handleNextAction(String userInput) {
        switch (userInput) {
            case "1": // Schedule an Appointment
                addDelayedResponse("Redirecting you to the Appointment Booking page...");
                messageHandler.postDelayed(() -> {
                    Intent appointmentIntent = new Intent(ChatroomActivity.this, AppointmentPage.class);
                    startActivity(appointmentIntent);
                }, MESSAGE_DELAY);
                break;
            case "2": // Chat with AI
                addDelayedResponse("Starting Chat with AI...");
                messageHandler.postDelayed(() -> {
                    // Pass the conclusion message to the ChatAI activity
                    Intent chatIntent = new Intent(ChatroomActivity.this, ChatAI.class);
                    chatIntent.putExtra("CONCLUSION_MESSAGE", conclusionMessage);  // Pass the conclusion message
                    startActivity(chatIntent); // Start ChatAI activity
                }, MESSAGE_DELAY);
                break;
            default:
                addDelayedResponse("Invalid option. Please choose 1, 2, or 3.");
                break;
        }
        isAwaitingNextAction = false;
    }
}