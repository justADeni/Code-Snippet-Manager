package core.highlight;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Detector {

    private static final Pattern pattern = Pattern.compile("[^.]+$");

    public String get(String name) {
        Matcher matcher = pattern.matcher(name);
        if (!matcher.find())
            return "text/plain";

        return switch (matcher.group()) {
            case "as" -> "text/actionscript";
            case "asm", "s" -> "text/asm";
            case "a65" -> "text/asm6502";
            case "bbcode" -> "text/bbcode";
            case "c" -> "text/c";
            case "clj" -> "text/clojure";
            case "cpp", "cc", "cxx", "hpp", "hxx" -> "text/cpp";
            case "cs" -> "text/cs";
            case "css" -> "text/css";
            case "csv" -> "text/csv";
            case "d" -> "text/d";
            case "dockerfile" -> "text/dockerfile";
            case "dart" -> "text/dart";
            case "pas", "dpr" -> "text/delphi";
            case "dtd" -> "text/dtd";
            case "f", "for", "f90" -> "text/fortran";
            case "go" -> "text/golang";
            case "groovy" -> "text/groovy";
            case "hbs" -> "text/handlebars";
            case "hosts" -> "text/hosts";
            case "htaccess" -> "text/htaccess";
            case "html", "htm" -> "text/html";
            case "ini" -> "text/ini";
            case "java" -> "text/java";
            case "js" -> "text/javascript";
            case "json" -> "text/json";
            case "jshintrc" -> "text/jshintrc";
            case "jsp" -> "text/jsp";
            case "kt", "kts" -> "text/kotlin";
            case "tex" -> "text/latex";
            case "less" -> "text/less";
            case "lisp", "lsp" -> "text/lisp";
            case "lua" -> "text/lua";
            case "mk", "makefile" -> "text/makefile";
            case "md" -> "text/markdown";
            case "mxml" -> "text/mxml";
            case "nsi", "nsh" -> "text/nsis";
            case "pl", "pm" -> "text/perl";
            case "php" -> "text/php";
            case "proto" -> "text/proto";
            case "properties" -> "text/properties";
            case "py" -> "text/python";
            case "rb" -> "text/ruby";
            case "rs" -> "text/rust";
            case "sas" -> "text/sas";
            case "scala" -> "text/scala";
            case "sql" -> "text/sql";
            case "tcl" -> "text/tcl";
            case "ts" -> "text/typescript";
            case "sh", "bash" -> "text/unix";
            case "vb" -> "text/vb";
            case "vhd", "vhdl" -> "text/vhdl";
            case "bat", "cmd" -> "text/bat";
            case "xml" -> "text/xml";
            case "yaml", "yml" -> "text/yaml";
            default -> "text/plain";
        };

    }

}
