package feo;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;


public class LexicalAnalyzer {
    private final InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;

    public LexicalAnalyzer(final InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(final int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '<':
                nextChar();
                curToken = Token.LANGLEBRACKET;
                break;
            case '>':
                nextChar();
                curToken = Token.RANGLEBRACKET;
                break;
            case ';':
                nextChar();
                curToken = Token.SEMICOLON;
                break;
            case ':':
                nextChar();
                curToken = Token.COLON;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                final String ident = parseIdent();
                if (ident.equals(TokenType.VAR.toString())) {
                    curToken = Token.VAR;
                } else if (ident.equals(TokenType.ARRAY.toString())) {
                    curToken = Token.ARRAY;
                } else {
                    curToken = new Token(TokenType.IDENT, ident);
                }
        }
    }

    private void illegalCharacter() throws ParseException {
        throw new ParseException("Illegal character " + (char) curChar, curPos);
    }

    private String parseIdent() throws ParseException {
        if (!Character.isLetter(curChar) && curChar != '_') {
            illegalCharacter();
        }
        final StringBuilder stringBuilder = new StringBuilder();
        do {
            stringBuilder.appendCodePoint(curChar);
            nextChar();
        } while (Character.isLetterOrDigit(curChar) || curChar == '_');
        return stringBuilder.toString();
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }
}