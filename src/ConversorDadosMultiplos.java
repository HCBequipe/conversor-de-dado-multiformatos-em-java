import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConversorDadosMultiplos {

    // Record para armazenar as propriedades do campo
    record Campo(String nome, String valor, String tipo) {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Campo> campos = new ArrayList<>();

        System.out.println("=== CONVERSOR PARA JSON, XML E YAML ===");
        System.out.println("Informe os dados no formato: NOME_CAMPO;VALOR;TIPO");
        System.out.println("Tipos aceitos: texto, data, data e hora, inteiro, flutuante, booleano, array");
        System.out.println("Digite 'SAIR' ou pressione ENTER em branco para finalizar as entradas.\n");

        while (true) {
            System.out.print("Entrada > ");
            String linha = scanner.nextLine().trim();

            if (linha.equalsIgnoreCase("SAIR") || linha.isEmpty()) {
                break;
            }

            String[] partes = linha.split(";");
            if (partes.length < 3) {
                System.out.println("⚠️ Entrada inválida! Certifique-se de usar o separador ';' informando Nome, Valor e Tipo.");
                continue;
            }

            String nome = partes[0].trim();
            String valor = partes[1].trim();
            String tipo = partes[2].trim().toLowerCase();

            campos.add(new Campo(nome, valor, tipo));
        }

        if (campos.isEmpty()) {
            System.out.println("\nNenhum campo foi informado.");
            scanner.close();
            return;
        }

        System.out.println("\n" + "=".repeat(40));
        System.out.println("--- SAÍDA EM JSON ---");
        System.out.println(gerarJson(campos));

        System.out.println("\n--- SAÍDA EM XML ---");
        System.out.println(gerarXml(campos));

        System.out.println("\n--- SAÍDA EM YAML ---");
        System.out.println(gerarYaml(campos));
        System.out.println("=".repeat(40));

        scanner.close();
    }

    // --- GERADOR DE JSON ---
    private static String gerarJson(List<Campo> campos) {
        StringBuilder json = new StringBuilder("{\n");

        for (int i = 0; i < campos.size(); i++) {
            Campo campo = campos.get(i);
            json.append("  \"").append(campo.nome()).append("\": ");

            switch (campo.tipo()) {
                case "inteiro", "flutuante", "booleano" -> json.append(campo.valor());
                case "array" -> json.append(formatarArrayJson(campo.valor()));
                default -> json.append("\"").append(campo.valor()).append("\""); // texto, data, data e hora
            }

            if (i < campos.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("}");
        return json.toString();
    }

    private static String formatarArrayJson(String valor) {
        String[] itens = valor.split(",");
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < itens.length; i++) {
            String item = itens[i].trim();
            if (item.matches("-?\\d+(\\.\\d+)?") || item.equalsIgnoreCase("true") || item.equalsIgnoreCase("false")) {
                sb.append(item);
            } else {
                sb.append("\"").append(item).append("\"");
            }
            if (i < itens.length - 1) sb.append(", ");
        }
        sb.append(" ]");
        return sb.toString();
    }

    // --- GERADOR DE XML ---
    private static String gerarXml(List<Campo> campos) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<dados>\n");

        for (Campo campo : campos) {
            String tag = campo.nome().replaceAll("\\s+", "_"); // Evita tags inválidas com espaços
            xml.append("  <").append(tag).append(" type=\"").append(campo.tipo()).append("\">");

            if (campo.tipo().equals("array")) {
                xml.append("\n");
                String[] itens = campo.valor().split(",");
                for (String item : itens) {
                    xml.append("    <item>").append(item.trim()).append("</item>\n");
                }
                xml.append("  </").append(tag).append(">\n");
            } else {
                xml.append(campo.valor()).append("</").append(tag).append(">\n");
            }
        }

        xml.append("</dados>");
        return xml.toString();
    }

    // --- GERADOR DE YAML ---
    private static String gerarYaml(List<Campo> campos) {
        StringBuilder yaml = new StringBuilder();

        for (Campo campo : campos) {
            yaml.append(campo.nome()).append(": ");

            switch (campo.tipo()) {
                case "inteiro", "flutuante", "booleano" -> yaml.append(campo.valor()).append("\n");
                case "array" -> {
                    yaml.append("\n");
                    String[] itens = campo.valor().split(",");
                    for (String item : itens) {
                        yaml.append("  - ").append(item.trim()).append("\n");
                    }
                }
                default -> yaml.append("\"").append(campo.valor()).append("\"\n"); // texto, data, etc.
            }
        }

        return yaml.toString();
    }
}