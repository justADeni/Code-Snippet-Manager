# Code Snippet Manager
![Snippets Screenshot](/screenshots/snippets.png)

<details>
  <summary style="font-size: 22px;">More screenshots</summary>

![Blank Screenshot](/screenshots/blank.png)
![New Group Screenshot](/screenshots/newgroup.png)
![Editing Screenshot](/screenshots/editing.png)
![Renaming Screenshot](/screenshots/renaming.png)
![Light Theme Screenshot](/screenshots/light.png)
</details>

# ✂️ Code Snippet Manager

A simple, lightweight Java tool that helps in managing code snippets.


### 🗂️ Organize snippets into **Groups**  
### 📋 Acts as a persistent **clipboard**
- Snippets are saved between sessions

### 🪢 Flexibility
- Snippets and Groups can be renamed by double-clicking their respective names

### 🎨 Theme Customization:  
Both the general app theme and the snippet code area can be customized.
  <details>
     <summary>General Themes</summary>
  
  - `Arc`
  - `Light Flat`
  - `Light Solarized`
  - `One Dark`
  - `Arc Dark`
  - `Nord`
  - `Monokai`
  - `Mac Dark` *(default)*
  </details>   

  <details>
    <summary>Highlight Themes</summary>
  
  - `Eclipse`
  - `Idea`
  - `VS`
  - `Monokai` *(default)*
  - `Obsidian`
  </details>


### 🧩 Syntax Highlighting:
  - Syntax highlighting is automatically enabled when snippet
  suffix corresponds to a supported language, e.g. `example.java` 
  or `react.js` or `SIMD.cpp`
  - comes with bracket completion and pair bracket highlighting
  - comes with autoindentation
  
  <details>
        <summary>Supported languages</summary>

  - `actionscript`
  - `asm`
  - `asm6502`
  - `bbcode`
  - `c`
  - `clojure`
  - `cpp`
  - `cs`
  - `css`
  - `csv`
  - `d`
  - `dockerfile`
  - `dart`
  - `delphi`
  - `dtd`
  - `fortran`
  - `golang`
  - `groovy`
  - `handlebars`
  - `hosts`
  - `htaccess`
  - `html`
  - `ini`
  - `java`
  - `javascript`
  - `json`
  - `jshintrc`
  - `jsp`
  - `kotlin`
  - `latex`
  - `less`
  - `lisp`
  - `lua`
  - `makefile`
  - `markdown`
  - `mxml`
  - `nsis`
  - `perl`
  - `php`
  - `proto`
  - `properties`
  - `python`
  - `ruby`
  - `rust`
  - `sas`
  - `scala`
  - `sql`
  - `tcl`
  - `typescript`
  - `unix`
  - `vb`
  - `vhdl`
  - `bat`
  - `xml`
  - `yaml`
  - `plaintext`

</details>

## 🚀 How to Run

### 🧾 Prerequisites
- Java **JDK 21+** installed

### 💾 Via Desktop Installer
* Download and run the installer from the [latest release](../../releases/tag/master).

### 🛠️ Running from Source
Alternatively, you can also run the project via the source code.
* Copy the project via the CLI, or by downloading the .zip file
* extract the zip and open the project in IntelliJ IDEA
* Navigate to CodeSnippetManager.java (which contains the main method)
* Click on run to execute the program

### 🏗️ Building the Project
To build the JAR file yourself:

```bash
  mvn package
```
After the build, you’ll find the .jar file in the `target` directory.
