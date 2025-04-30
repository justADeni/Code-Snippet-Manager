package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.objects.Group;
import core.objects.Snippet;
import core.panels.GroupsTabbedPanel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    // File where data will be stored
    private static final String DATA_FILE = "snippets_data.json";
    private final Gson gson;
    private final GroupsTabbedPanel groupsTabbedPanel;

    public DataManager(GroupsTabbedPanel groupsTabbedPanel) {
        this.groupsTabbedPanel = groupsTabbedPanel;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public void saveData() {
        List<Map<String, Object>> groupsData = new ArrayList<>();

        for (Group group : groupsTabbedPanel.groups) {
            Map<String, Object> groupData = new HashMap<>();
            groupData.put("groupName", group.groupName);

            List<Map<String, String>> snippetsData = new ArrayList<>();
            for (Snippet snippet : group.snippets) {
                Map<String, String> snippetData = new HashMap<>();
                snippetData.put("name", snippet.getName());
                snippetData.put("code", snippet.getText());
                snippetsData.add(snippetData);
            }

            groupData.put("snippets", snippetsData);
            groupsData.add(groupData);
        }

        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            gson.toJson(groupsData, writer);
            System.out.println("Data saved successfully to " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean loadData() {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println("No saved data found at " + DATA_FILE);
            return false;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> groupsData = gson.fromJson(reader, listType);

            if (groupsData == null) {
                return false;
            }

            // Clear existing groups
            groupsTabbedPanel.groups.clear();
            groupsTabbedPanel.removeAll();

            // Recreate groups and snippets
            for (Map<String, Object> groupData : groupsData) {
                String groupName = (String) groupData.get("groupName");
                groupsTabbedPanel.addGroup(groupName);

                // Get the current group (the last one added)
                Group currentGroup = groupsTabbedPanel.groups.getLast();

                // Add snippets to the group
                List<Map<String, String>> snippetsData = (List<Map<String, String>>) groupData.get("snippets");
                if (snippetsData != null) {
                    for (Map<String, String> snippetData : snippetsData) {
                        String snippetName = snippetData.get("name");
                        String snippetCode = snippetData.get("code");

                        // Add the snippet to the group
                        currentGroup.addSnippet(snippetName, snippetCode);

                        // Get the last added snippet and set its code
                        Snippet snippet = currentGroup.snippets.getLast();
                        snippet.revalidate();
                    }
                }
            }

            // If there's at least one group, select the first one
            if (!groupsTabbedPanel.groups.isEmpty()) {
                groupsTabbedPanel.setSelectedIndex(0);
            }

            System.out.println("Data loaded successfully from " + DATA_FILE);
            return true;
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}