package ru.akirakozov.sd.refactoring.util;

import java.util.function.Consumer;

public final class Html {
    public static String writeBody(Consumer<StringBuilder> bodyWriter) {
        final StringBuilder html = new StringBuilder();
        
        html.append("<html><body>\n");
        
        bodyWriter.accept(html);
        
        html.append("</body></html>\n");
        
        return html.toString();
    }
}
