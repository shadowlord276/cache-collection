package com.evotek.cache.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;

/*
 * LinhLH
 */

@Slf4j
public class StringUtil {

    /** The empty string array. */
    private static String[] _emptyStringArray = new String[0];

    /** The Constant _HEX_DIGITS. */
    private static final char[] _HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };
    /**
     * Highlight.
     *
     * @param s
     *            the s
     * @param pattern
     *            the pattern
     * @param highlight1
     *            the highlight 1
     * @param highlight2
     *            the highlight 2
     * @return the string
     */
    private static String _highlight(String s, Pattern pattern, String highlight1, String highlight2) {

        StringTokenizer st = new StringTokenizer(s);

        if (st.countTokens() == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * st.countTokens() - 1);

        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            Matcher matcher = pattern.matcher(token);

            if (matcher.find()) {
                StringBuffer hightlighted = new StringBuffer();

                do {
                    matcher.appendReplacement(hightlighted, highlight1 + matcher.group() + highlight2);
                } while (matcher.find());

                matcher.appendTail(hightlighted);

                sb.append(hightlighted);
            } else {
                sb.append(token);
            }

            if (st.hasMoreTokens()) {
                sb.append(StringPool.SPACE);
            }
        }

        return sb.toString();
    }

    /**
     * Checks if is trimable.
     *
     * @param c
     *            the c
     * @param exceptions
     *            the exceptions
     * @return true, if successful
     */
    private static boolean _isTrimable(char c, char[] exceptions) {
        if (exceptions != null && exceptions.length > 0) {
            for (char exception : exceptions) {
                if (c == exception) {
                    return false;
                }
            }
        }

        return Character.isWhitespace(c);
    }

    /**
     * Adds the.
     *
     * @param s
     *            the s
     * @param add
     *            the add
     * @return the string
     */
    public static String add(String s, String add) {
        return add(s, add, StringPool.COMMA);
    }

    /**
     * Adds the.
     *
     * @param s
     *            the s
     * @param add
     *            the add
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String add(String s, String add, String delimiter) {
        return add(s, add, delimiter, false);
    }

    /**
     * Adds the.
     *
     * @param s
     *            the s
     * @param add
     *            the add
     * @param delimiter
     *            the delimiter
     * @param allowDuplicates
     *            the allow duplicates
     * @return the string
     */
    public static String add(String s, String add, String delimiter, boolean allowDuplicates) {

        if (add == null || delimiter == null) {
            return null;
        }

        if (s == null) {
            s = StringPool.BLANK;
        }

        if (allowDuplicates || !contains(s, add, delimiter)) {
            StringBuilder sb = new StringBuilder();

            sb.append(s);

            if (Validator.isNull(s) || s.endsWith(delimiter)) {
                sb.append(add);
                sb.append(delimiter);
            } else {
                sb.append(delimiter);
                sb.append(add);
                sb.append(delimiter);
            }

            s = sb.toString();
        }

        return s;
    }

    /**
     * Append parenthetical suffix.
     *
     * @param s
     *            the s
     * @param suffix
     *            the suffix
     * @return the string
     */
    public static String appendParentheticalSuffix(String s, int suffix) {
        if (Pattern.matches(".* \\(" + String.valueOf(suffix - 1) + "\\)", s)) {
            int pos = s.lastIndexOf(" (");

            s = s.substring(0, pos);
        }

        return appendParentheticalSuffix(s, String.valueOf(suffix));
    }

    /**
     * Append parenthetical suffix.
     *
     * @param s
     *            the s
     * @param suffix
     *            the suffix
     * @return the string
     */
    public static String appendParentheticalSuffix(String s, String suffix) {
        StringBuilder sb = new StringBuilder(5);

        sb.append(s);
        sb.append(StringPool.SPACE);
        sb.append(StringPool.OPEN_PARENTHESIS);
        sb.append(suffix);
        sb.append(StringPool.CLOSE_PARENTHESIS);

        return sb.toString();
    }

    /**
     * Bytes to hex string.
     *
     * @param bytes
     *            the bytes
     * @return the string
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for (byte b : bytes) {
            String hex = Integer.toHexString(0x0100 + (b & 0x00FF)).substring(1);

            if (hex.length() < 2) {
                sb.append("0");
            }

            sb.append(hex);
        }

        return sb.toString();
    }

    /**
     * Contains.
     *
     * @param s
     *            the s
     * @param text
     *            the text
     * @return true, if successful
     */
    public static boolean contains(String s, String text) {
        return contains(s, text, StringPool.COMMA);
    }

    /**
     * Contains.
     *
     * @param s
     *            the s
     * @param text
     *            the text
     * @param delimiter
     *            the delimiter
     * @return true, if successful
     */
    public static boolean contains(String s, String text, String delimiter) {
        if (s == null || text == null || delimiter == null) {
            return false;
        }

        if (!s.endsWith(delimiter)) {
            s = s + delimiter;
        }

        String dtd = delimiter + text + delimiter;

        int pos = s.indexOf(dtd);

        if (pos == -1) {
            String td = text + delimiter;

            if (s.startsWith(td)) {
                return true;
            }

            return false;
        }

        return true;
    }

    /**
     * Count.
     *
     * @param s
     *            the s
     * @param text
     *            the text
     * @return the int
     */
    public static int count(String s, String text) {
        if (s == null || text == null) {
            return 0;
        }

        int count = 0;

        int pos = s.indexOf(text);

        while (pos != -1) {
            pos = s.indexOf(text, pos + text.length());

            count++;
        }

        return count;
    }

    /**
     * Ends with.
     *
     * @param s
     *            the s
     * @param end
     *            the end
     * @return true, if successful
     */
    public static boolean endsWith(String s, char end) {
        return endsWith(s, new Character(end).toString());
    }

    /**
     * Ends with.
     *
     * @param s
     *            the s
     * @param end
     *            the end
     * @return true, if successful
     */
    public static boolean endsWith(String s, String end) {
        if (s == null || end == null) {
            return false;
        }

        if (end.length() > s.length()) {
            return false;
        }

        String temp = s.substring(s.length() - end.length(), s.length());

        if (temp.equalsIgnoreCase(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Extract.
     *
     * @param s
     *            the s
     * @param chars
     *            the chars
     * @return the string
     */
    public static String extract(String s, char[] chars) {
        if (s == null) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder();

        for (char c1 : s.toCharArray()) {
            for (char c2 : chars) {
                if (c1 == c2) {
                    sb.append(c1);

                    break;
                }
            }
        }

        return sb.toString();
    }

    /**
     * Extract chars.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String extractChars(String s) {
        if (s == null) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder();

        char[] chars = s.toCharArray();

        for (char c : chars) {
            if (Validator.isChar(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Extract digits.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String extractDigits(String s) {
        if (s == null) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder();

        char[] chars = s.toCharArray();

        for (char c : chars) {
            if (Validator.isDigit(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Extract first.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String extractFirst(String s, char delimiter) {
        if (s == null) {
            return null;
        } else {
            int index = s.indexOf(delimiter);

            if (index < 0) {
                return null;
            } else {
                return s.substring(0, index);
            }
        }
    }

    /**
     * Extract first.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String extractFirst(String s, String delimiter) {
        if (s == null) {
            return null;
        } else {
            int index = s.indexOf(delimiter);

            if (index < 0) {
                return null;
            } else {
                return s.substring(0, index);
            }
        }
    }

    /**
     * Extract last.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String extractLast(String s, char delimiter) {
        if (s == null) {
            return null;
        } else {
            int index = s.lastIndexOf(delimiter);

            if (index < 0) {
                return null;
            } else {
                return s.substring(index + 1);
            }
        }
    }

    /**
     * Extract last.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String extractLast(String s, String delimiter) {
        if (s == null) {
            return null;
        } else {
            int index = s.lastIndexOf(delimiter);

            if (index < 0) {
                return null;
            } else {
                return s.substring(index + delimiter.length());
            }
        }
    }

    /**
     * Format number.
     *
     * @param number
     *            the number
     * @return the string
     */
    public static String formatNumber(int number) {
        return formatNumber(number, Locale.getDefault());
    }

    /**
     * Format number.
     *
     * @param number
     *            the number
     * @param locale
     *            the locale
     * @return the string
     */
    public static String formatNumber(int number, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);

        return nf.format(number);
    }

    /**
     * Format number.
     *
     * @param number
     *            the number
     * @return the string
     */
    public static String formatNumber(long number) {
        return formatNumber(number, Locale.getDefault());
    }

    /**
     * Format number.
     *
     * @param number
     *            the number
     * @param locale
     *            the locale
     * @return the string
     */
    public static String formatNumber(long number, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);

        return nf.format(number);
    }

    /**
     * Format number.
     *
     * @param number
     *            the number
     * @return the string
     */
    public static String formatNumber(Object number) {
        return formatNumber(number, Locale.getDefault());
    }

    /**
     * Format number.
     *
     * @param number
     *            the number
     * @param locale
     *            the locale
     * @return the string
     */
    public static String formatNumber(Object number, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);

        return nf.format(number);
    }

    /**
     * Hide.
     *
     * @param s
     *            the s
     * @param fromIndex
     *            the from index
     * @param toIndex
     *            the to index
     * @param mask
     *            the mask
     * @return the string
     */
    public static String hide(String s, int fromIndex, int toIndex, String mask) {
        if (Validator.isNull(s)
                || Validator.isNull(mask)
                || fromIndex < 0
                || toIndex > s.length()
                || fromIndex > toIndex) {
            return s;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(s.substring(0, fromIndex));

        // for (int i = fromIndex; i < toIndex; i++) {
        sb.append(mask);
        sb.append(mask);
        sb.append(mask);
        // }

        sb.append(s.substring(toIndex, s.length()));

        return sb.toString();
    }

    /**
     * Highlight.
     *
     * @param s
     *            the s
     * @param keywords
     *            the keywords
     * @return the string
     */
    public static String highlight(String s, String keywords) {
        return highlight(s, keywords, "<span class=\"highlight\">", "</span>");
    }

    /**
     * Highlight.
     *
     * @param s
     *            the s
     * @param keywords
     *            the keywords
     * @param highlight1
     *            the highlight 1
     * @param highlight2
     *            the highlight 2
     * @return the string
     */
    public static String highlight(String s, String keywords, String highlight1, String highlight2) {

        if (Validator.isNull(s) || Validator.isNull(keywords)) {
            return s;
        }

        Pattern pattern = Pattern.compile(Pattern.quote(keywords), Pattern.CASE_INSENSITIVE);

        return _highlight(s, pattern, highlight1, highlight2);
    }

    /**
     * Highlight.
     *
     * @param s
     *            the s
     * @param queryTerms
     *            the query terms
     * @return the string
     */
    public static String highlight(String s, String[] queryTerms) {
        return highlight(s, queryTerms, "<span class=\"highlight\">", "</span>");
    }

    /**
     * Highlight.
     *
     * @param s
     *            the s
     * @param queryTerms
     *            the query terms
     * @param highlight1
     *            the highlight 1
     * @param highlight2
     *            the highlight 2
     * @return the string
     */
    public static String highlight(String s, String[] queryTerms, String highlight1, String highlight2) {

        if (Validator.isNull(s) || Validator.isNull(queryTerms)) {
            return s;
        }

        if (queryTerms.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * queryTerms.length - 1);

        for (int i = 0; i < queryTerms.length; i++) {
            sb.append(Pattern.quote(queryTerms[i].trim()));

            if (i + 1 < queryTerms.length) {
                sb.append(StringPool.PIPE);
            }
        }

        int flags = Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

        Pattern pattern = Pattern.compile(sb.toString(), flags);

        return _highlight(s, pattern, highlight1, highlight2);
    }

    /**
     * Insert.
     *
     * @param s
     *            the s
     * @param insert
     *            the insert
     * @param offset
     *            the offset
     * @return the string
     */
    public static String insert(String s, String insert, int offset) {
        if (s == null) {
            return null;
        }

        if (insert == null) {
            return s;
        }

        if (offset > s.length()) {
            return s + insert;
        } else {
            String prefix = s.substring(0, offset);
            String postfix = s.substring(offset);

            return prefix + insert + postfix;
        }
    }

    /**
     * Join.
     *
     * @param list
     *            the list
     * @param sparator
     *            the sparator
     * @return the string
     */
    public static String join(Collection<?> list, String sparator) {
        return StringUtils.join(list, sparator);
    }

    /**
     * Join.
     *
     * @param array
     *            the array
     * @param sparator
     *            the sparator
     * @return the string
     */
    public static String join(int[] array, String sparator) {
        if (array == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (int i : array) {
            sb.append(i);
            sb.append(sparator);
        }

        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : StringPool.BLANK;
    }

    /**
     * Join.
     *
     * @param array
     *            the array
     * @param sparator
     *            the sparator
     * @return the string
     */
    public static String join(Object[] array, String sparator) {
        return StringUtils.join(array, sparator);
    }

    /**
     * Join.
     *
     * @param array
     *            the array
     * @param sparator
     *            the sparator
     * @return the string
     */
    public static String join(String[] array, String sparator) {
        return StringUtils.join(array, sparator);
    }

    /**
     * Lower case.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String lowerCase(String s) {
        if (s == null) {
            return null;
        } else {
            return s.toLowerCase();
        }
    }

    /**
     * Lower case first letter.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String lowerCaseFirstLetter(String s) {
        char[] chars = s.toCharArray();

        if (chars[0] >= 65 && chars[0] <= 90) {
            chars[0] = (char) (chars[0] + 32);
        }

        return new String(chars);
    }

    /**
     * Lower case first letter.
     *
     * @param s
     *            the s
     * @param defaultString
     *            the default string
     * @return the string
     */
    public static String lowerCaseFirstLetter(String s, String defaultString) {
        if (Validator.isNotNull(s)) {
            return lowerCaseFirstLetter(s);
        } else {
            return defaultString;
        }
    }

    /**
     * Matches.
     *
     * @param s
     *            the s
     * @param pattern
     *            the pattern
     * @return true, if successful
     */
    public static boolean matches(String s, String pattern) {
        String[] array = pattern.split("\\*");

        for (String element : array) {
            int pos = s.indexOf(element);

            if (pos == -1) {
                return false;
            }

            s = s.substring(pos + element.length());
        }

        return true;
    }

    /**
     * Matches ignore case.
     *
     * @param s
     *            the s
     * @param pattern
     *            the pattern
     * @return true, if successful
     */
    public static boolean matchesIgnoreCase(String s, String pattern) {
        return matches(lowerCase(s), lowerCase(pattern));
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(boolean[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(boolean[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(char[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(char[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Merge.
     *
     * @param col
     *            the col
     * @return the string
     */
    public static String merge(Collection<?> col) {
        return merge(col, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param col
     *            the col
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(Collection<?> col, String delimiter) {
        if (col == null) {
            return null;
        }

        return merge(col.toArray(new Object[col.size()]), delimiter);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(double[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(double[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(float[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(float[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(int[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(int[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(long[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(long[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(Object[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(Object[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @return the string
     */
    public static String merge(short[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * Merge.
     *
     * @param array
     *            the array
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String merge(short[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return StringPool.BLANK;
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]).trim());

            if (i + 1 != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * Quote.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String quote(String s) {
        return quote(s, CharPool.APOSTROPHE);
    }

    /**
     * Quote.
     *
     * @param s
     *            the s
     * @param quote
     *            the quote
     * @return the string
     */
    public static String quote(String s, char quote) {
        if (s == null) {
            return null;
        }

        return quote(s, String.valueOf(quote));
    }

    /**
     * Quote.
     *
     * @param s
     *            the s
     * @param quote
     *            the quote
     * @return the string
     */
    public static String quote(String s, String quote) {
        if (s == null) {
            return null;
        }

        return quote + s + quote;
    }

    /**
     * Removes the.
     *
     * @param s
     *            the s
     * @param remove
     *            the remove
     * @return the string
     */
    public static String remove(String s, String remove) {
        return remove(s, remove, StringPool.COMMA);
    }

    /**
     * Removes the.
     *
     * @param s
     *            the s
     * @param remove
     *            the remove
     * @param delimiter
     *            the delimiter
     * @return the string
     */
    public static String remove(String s, String remove, String delimiter) {
        if (s == null || remove == null || delimiter == null) {
            return null;
        }

        if (Validator.isNotNull(s) && !s.endsWith(delimiter)) {
            s += delimiter;
        }

        String drd = delimiter + remove + delimiter;

        String rd = remove + delimiter;

        while (contains(s, remove, delimiter)) {
            int pos = s.indexOf(drd);

            if (pos == -1) {
                if (s.startsWith(rd)) {
                    int x = remove.length() + delimiter.length();
                    int y = s.length();

                    s = s.substring(x, y);
                }
            } else {
                int x = pos + remove.length() + delimiter.length();
                int y = s.length();

                String temp = s.substring(0, pos);

                s = temp + s.substring(x, y);
            }
        }

        return s;
    }

    /**
     * Replace.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replace(String s, char oldSub, char newSub) {
        if (s == null) {
            return null;
        }

        return s.replace(oldSub, newSub);
    }

    /**
     * Replace.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replace(String s, char oldSub, String newSub) {
        if (s == null || newSub == null) {
            return null;
        }

        // The number 5 is arbitrary and is used as extra padding to reduce
        // buffer expansion
        StringBuilder sb = new StringBuilder(s.length() + 5 * newSub.length());

        char[] chars = s.toCharArray();

        for (char c : chars) {
            if (c == oldSub) {
                sb.append(newSub);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Replace.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replace(String s, String oldSub, String newSub) {
        return replace(s, oldSub, newSub, 0);
    }

    /**
     * Replace.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @param fromIndex
     *            the from index
     * @return the string
     */
    public static String replace(String s, String oldSub, String newSub, int fromIndex) {

        if (s == null) {
            return null;
        }

        if (oldSub == null || oldSub.equals(StringPool.BLANK)) {
            return s;
        }

        if (newSub == null) {
            newSub = StringPool.BLANK;
        }

        int y = s.indexOf(oldSub, fromIndex);

        if (y >= 0) {
            StringBuilder sb = new StringBuilder();

            int length = oldSub.length();
            int x = 0;

            while (x <= y) {
                sb.append(s.substring(x, y));
                sb.append(newSub);

                x = y + length;
                y = s.indexOf(oldSub, x);
            }

            sb.append(s.substring(x));

            return sb.toString();
        } else {
            return s;
        }
    }

    /**
     * Replace.
     *
     * @param s
     *            the s
     * @param begin
     *            the begin
     * @param end
     *            the end
     * @param values
     *            the values
     * @return the string
     */
    public static String replace(String s, String begin, String end, Map<String, String> values) {

        StringBuilder sb = replaceToStringBuilder(s, begin, end, values);

        return sb.toString();
    }

    /**
     * Replace.
     *
     * @param s
     *            the s
     * @param oldSubs
     *            the old subs
     * @param newSubs
     *            the new subs
     * @return the string
     */
    public static String replace(String s, String[] oldSubs, String[] newSubs) {
        if (s == null || oldSubs == null || newSubs == null) {
            return null;
        }

        if (oldSubs.length != newSubs.length) {
            return s;
        }

        for (int i = 0; i < oldSubs.length; i++) {
            s = replace(s, oldSubs[i], newSubs[i]);
        }

        return s;
    }

    /**
     * Replace.
     *
     * @param s
     *            the s
     * @param oldSubs
     *            the old subs
     * @param newSubs
     *            the new subs
     * @param exactMatch
     *            the exact match
     * @return the string
     */
    public static String replace(String s, String[] oldSubs, String[] newSubs, boolean exactMatch) {

        if (s == null || oldSubs == null || newSubs == null) {
            return null;
        }

        if (oldSubs.length != newSubs.length) {
            return s;
        }

        if (!exactMatch) {
            replace(s, oldSubs, newSubs);
        } else {
            for (int i = 0; i < oldSubs.length; i++) {
                s = s.replaceAll("\\b" + oldSubs[i] + "\\b", newSubs[i]);
            }
        }

        return s;
    }

    /**
     * Replace first.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replaceFirst(String s, char oldSub, char newSub) {
        if (s == null) {
            return null;
        }

        return replaceFirst(s, String.valueOf(oldSub), String.valueOf(newSub));
    }

    /**
     * Replace first.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replaceFirst(String s, char oldSub, String newSub) {
        if (s == null || newSub == null) {
            return null;
        }

        return replaceFirst(s, String.valueOf(oldSub), newSub);
    }

    /**
     * Replace first.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replaceFirst(String s, String oldSub, String newSub) {
        if (s == null || oldSub == null || newSub == null) {
            return null;
        }

        if (oldSub.equals(newSub)) {
            return s;
        }

        int y = s.indexOf(oldSub);

        if (y >= 0) {
            return s.substring(0, y) + newSub + s.substring(y + oldSub.length());
        } else {
            return s;
        }
    }

    /**
     * Replace first.
     *
     * @param s
     *            the s
     * @param oldSubs
     *            the old subs
     * @param newSubs
     *            the new subs
     * @return the string
     */
    public static String replaceFirst(String s, String[] oldSubs, String[] newSubs) {

        if (s == null || oldSubs == null || newSubs == null) {
            return null;
        }

        if (oldSubs.length != newSubs.length) {
            return s;
        }

        for (int i = 0; i < oldSubs.length; i++) {
            s = replaceFirst(s, oldSubs[i], newSubs[i]);
        }

        return s;
    }

    /**
     * Replace last.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replaceLast(String s, char oldSub, char newSub) {
        if (s == null) {
            return null;
        }

        return replaceLast(s, String.valueOf(oldSub), String.valueOf(newSub));
    }

    /**
     * Replace last.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replaceLast(String s, char oldSub, String newSub) {
        if (s == null || newSub == null) {
            return null;
        }

        return replaceLast(s, String.valueOf(oldSub), newSub);
    }

    /**
     * Replace last.
     *
     * @param s
     *            the s
     * @param oldSub
     *            the old sub
     * @param newSub
     *            the new sub
     * @return the string
     */
    public static String replaceLast(String s, String oldSub, String newSub) {
        if (s == null || oldSub == null || newSub == null) {
            return null;
        }

        if (oldSub.equals(newSub)) {
            return s;
        }

        int y = s.lastIndexOf(oldSub);

        if (y >= 0) {
            return s.substring(0, y) + newSub + s.substring(y + oldSub.length());
        } else {
            return s;
        }
    }

    /**
     * Replace last.
     *
     * @param s
     *            the s
     * @param oldSubs
     *            the old subs
     * @param newSubs
     *            the new subs
     * @return the string
     */
    public static String replaceLast(String s, String[] oldSubs, String[] newSubs) {

        if (s == null || oldSubs == null || newSubs == null) {
            return null;
        }

        if (oldSubs.length != newSubs.length) {
            return s;
        }

        for (int i = 0; i < oldSubs.length; i++) {
            s = replaceLast(s, oldSubs[i], newSubs[i]);
        }

        return s;
    }

    /**
     * Replace to string builder.
     *
     * @param s
     *            the s
     * @param begin
     *            the begin
     * @param end
     *            the end
     * @param values
     *            the values
     * @return the string builder
     */
    public static StringBuilder replaceToStringBuilder(String s, String begin, String end, Map<String, String> values) {

        if (s == null || begin == null || end == null || values == null || values.size() == 0) {

            return new StringBuilder(s);
        }

        StringBuilder sb = new StringBuilder(values.size() * 2 + 1);

        int pos = 0;

        while (true) {
            int x = s.indexOf(begin, pos);
            int y = s.indexOf(end, x + begin.length());

            if (x == -1 || y == -1) {
                sb.append(s.substring(pos, s.length()));

                break;
            } else {
                sb.append(s.substring(pos, x));

                String oldValue = s.substring(x + begin.length(), y);

                String newValue = values.get(oldValue);

                if (newValue == null) {
                    newValue = oldValue;
                }

                sb.append(newValue);

                pos = y + end.length();
            }
        }

        return sb;
    }

    /**
     * Reverse.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String reverse(String s) {
        if (s == null) {
            return null;
        }

        char[] chars = s.toCharArray();
        char[] reverse = new char[chars.length];

        for (int i = 0; i < chars.length; i++) {
            reverse[i] = chars[chars.length - i - 1];
        }

        return new String(reverse);
    }

    /**
     * Safe path.
     *
     * @param path
     *            the path
     * @return the string
     */
    public static String safePath(String path) {
        return replace(path, StringPool.DOUBLE_SLASH, StringPool.SLASH);
    }

    /**
     * Shorten.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String shorten(String s) {
        return shorten(s, 20);
    }

    /**
     * Shorten.
     *
     * @param s
     *            the s
     * @param length
     *            the length
     * @return the string
     */
    public static String shorten(String s, int length) {
        return shorten(s, length, "...");
    }

    /**
     * Shorten.
     *
     * @param s
     *            the s
     * @param length
     *            the length
     * @param suffix
     *            the suffix
     * @return the string
     */
    public static String shorten(String s, int length, String suffix) {
        if (s == null || suffix == null) {
            return null;
        }

        if (s.length() > length) {
            for (int j = length; j >= 0; j--) {
                if (Character.isWhitespace(s.charAt(j))) {
                    length = j;

                    break;
                }
            }

            String temp = s.substring(0, length);

            s = temp + suffix;
        }

        return s;
    }

    /**
     * Shorten.
     *
     * @param s
     *            the s
     * @param suffix
     *            the suffix
     * @return the string
     */
    public static String shorten(String s, String suffix) {
        return shorten(s, 20, suffix);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @return the string[]
     */
    public static String[] split(String s) {
        return split(s, CharPool.COMMA);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param x
     *            the x
     * @return the boolean[]
     */
    public static boolean[] split(String s, boolean x) {
        return split(s, StringPool.COMMA, x);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @return the string[]
     */
    public static String[] split(String s, char delimiter) {
        if (Validator.isNull(s)) {
            return _emptyStringArray;
        }

        s = s.trim();

        if (s.length() == 0) {
            return _emptyStringArray;
        }

        if (delimiter == CharPool.RETURN || delimiter == CharPool.NEW_LINE) {

            return splitLines(s);
        }

        List<String> nodeValues = new ArrayList<>();

        int offset = 0;
        int pos = s.indexOf(delimiter, offset);

        while (pos != -1) {
            nodeValues.add(s.substring(offset, pos));

            offset = pos + 1;
            pos = s.indexOf(delimiter, offset);
        }

        if (offset < s.length()) {
            nodeValues.add(s.substring(offset));
        }

        return nodeValues.toArray(new String[nodeValues.size()]);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param x
     *            the x
     * @return the double[]
     */
    public static double[] split(String s, double x) {
        return split(s, StringPool.COMMA, x);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param x
     *            the x
     * @return the float[]
     */
    public static float[] split(String s, float x) {
        return split(s, StringPool.COMMA, x);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param x
     *            the x
     * @return the int[]
     */
    public static int[] split(String s, int x) {
        return split(s, StringPool.COMMA, x);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param x
     *            the x
     * @return the long[]
     */
    public static long[] split(String s, long x) {
        return split(s, StringPool.COMMA, x);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param x
     *            the x
     * @return the short[]
     */
    public static short[] split(String s, short x) {
        return split(s, StringPool.COMMA, x);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @return the string[]
     */
    public static String[] split(String s, String delimiter) {
        if (Validator.isNull(s) || delimiter == null || delimiter.equals(StringPool.BLANK)) {

            return _emptyStringArray;
        }

        s = s.trim();

        if (s.equals(delimiter)) {
            return _emptyStringArray;
        }

        if (delimiter.length() == 1) {
            return split(s, delimiter.charAt(0));
        }

        List<String> nodeValues = new ArrayList<>();

        int offset = 0;
        int pos = s.indexOf(delimiter, offset);

        while (pos != -1) {
            nodeValues.add(s.substring(offset, pos));

            offset = pos + delimiter.length();
            pos = s.indexOf(delimiter, offset);
        }

        if (offset < s.length()) {
            nodeValues.add(s.substring(offset));
        }

        return nodeValues.toArray(new String[nodeValues.size()]);
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @param x
     *            the x
     * @return the boolean[]
     */
    public static boolean[] split(String s, String delimiter, boolean x) {
        String[] array = split(s, delimiter);
        boolean[] newArray = new boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            boolean value = x;

            try {
                value = Boolean.valueOf(array[i]).booleanValue();
            } catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @param x
     *            the x
     * @return the double[]
     */
    public static double[] split(String s, String delimiter, double x) {
        String[] array = split(s, delimiter);
        double[] newArray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            double value = x;

            try {
                value = Double.parseDouble(array[i]);
            } catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @param x
     *            the x
     * @return the float[]
     */
    public static float[] split(String s, String delimiter, float x) {
        String[] array = split(s, delimiter);
        float[] newArray = new float[array.length];

        for (int i = 0; i < array.length; i++) {
            float value = x;

            try {
                value = Float.parseFloat(array[i]);
            } catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @param x
     *            the x
     * @return the int[]
     */
    public static int[] split(String s, String delimiter, int x) {
        String[] array = split(s, delimiter);
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            int value = x;

            try {
                value = Integer.parseInt(array[i]);
            } catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @param x
     *            the x
     * @return the long[]
     */
    public static long[] split(String s, String delimiter, long x) {
        String[] array = split(s, delimiter);
        long[] newArray = new long[array.length];

        for (int i = 0; i < array.length; i++) {
            long value = x;

            try {
                value = Long.parseLong(array[i]);
            } catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * Split.
     *
     * @param s
     *            the s
     * @param delimiter
     *            the delimiter
     * @param x
     *            the x
     * @return the short[]
     */
    public static short[] split(String s, String delimiter, short x) {
        String[] array = split(s, delimiter);
        short[] newArray = new short[array.length];

        for (int i = 0; i < array.length; i++) {
            short value = x;

            try {
                value = Short.parseShort(array[i]);
            } catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * Split into line.
     *
     * @param input
     *            the input
     * @param maxCharInLine
     *            the max char in line
     * @return the string[]
     */
    public static String[] splitIntoLine(String input, int maxCharInLine) {

        StringTokenizer token = new StringTokenizer(input, StringPool.SPACE);

        StringBuilder output = new StringBuilder(input.length());

        int lineLength = 0;

        while (token.hasMoreTokens()) {
            String word = token.nextToken();

            while (word.length() > maxCharInLine) {
                output.append(word.substring(0, maxCharInLine - lineLength));
                output.append(CharPool.NEW_LINE);

                word = word.substring(maxCharInLine - lineLength);

                lineLength = 0;
            }

            if (lineLength + word.length() > maxCharInLine) {
                output.append(CharPool.NEW_LINE);

                lineLength = 0;
            }

            output.append(word);
            output.append(StringPool.SPACE);

            lineLength += word.length() + 1;
        }

        return splitLines(output.toString());
    }

    /**
     * Split lines.
     *
     * @param s
     *            the s
     * @return the string[]
     */
    public static String[] splitLines(String s) {
        if (Validator.isNull(s)) {
            return _emptyStringArray;
        }

        s = s.trim();

        List<String> lines = new ArrayList<>();

        int lastIndex = 0;

        while (true) {
            int returnIndex = s.indexOf(CharPool.RETURN, lastIndex);
            int newLineIndex = s.indexOf(CharPool.NEW_LINE, lastIndex);

            if (returnIndex == -1 && newLineIndex == -1) {
                break;
            }

            if (returnIndex == -1) {
                lines.add(s.substring(lastIndex, newLineIndex));

                lastIndex = newLineIndex + 1;
            } else if (newLineIndex == -1) {
                lines.add(s.substring(lastIndex, returnIndex));

                lastIndex = returnIndex + 1;
            } else if (newLineIndex < returnIndex) {
                lines.add(s.substring(lastIndex, newLineIndex));

                lastIndex = newLineIndex + 1;
            } else {
                lines.add(s.substring(lastIndex, returnIndex));

                lastIndex = returnIndex + 1;

                if (lastIndex == newLineIndex) {
                    lastIndex++;
                }
            }
        }

        if (lastIndex < s.length()) {
            lines.add(s.substring(lastIndex));
        }

        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Starts with.
     *
     * @param s
     *            the s
     * @param begin
     *            the begin
     * @return true, if successful
     */
    public static boolean startsWith(String s, char begin) {
        return startsWith(s, new Character(begin).toString());
    }

    /**
     * Starts with.
     *
     * @param s
     *            the s
     * @param start
     *            the start
     * @return true, if successful
     */
    public static boolean startsWith(String s, String start) {
        if (s == null || start == null) {
            return false;
        }

        if (start.length() > s.length()) {
            return false;
        }

        String temp = s.substring(0, start.length());

        if (temp.equalsIgnoreCase(start)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Starts with weight.
     *
     * @param s1
     *            the s 1
     * @param s2
     *            the s 2
     * @return the int
     */
    public static int startsWithWeight(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0;
        }

        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();

        int i = 0;

        for (; i < chars1.length && i < chars2.length; i++) {
            if (chars1[i] != chars2[i]) {
                break;
            }
        }

        return i;
    }

    /**
     * Strip.
     *
     * @param s
     *            the s
     * @param remove
     *            the remove
     * @return the string
     */
    public static String strip(String s, char remove) {
        if (s == null) {
            return null;
        }

        int x = s.indexOf(remove);

        if (x < 0) {
            return s;
        }

        int y = 0;

        StringBuilder sb = new StringBuilder(s.length());

        while (x >= 0) {
            sb.append(s.subSequence(y, x));

            y = x + 1;

            x = s.indexOf(remove, y);
        }

        sb.append(s.substring(y));

        return sb.toString();
    }

    /**
     * Strip between.
     *
     * @param s
     *            the s
     * @param begin
     *            the begin
     * @param end
     *            the end
     * @return the string
     */
    public static String stripBetween(String s, String begin, String end) {
        if (s == null || begin == null || end == null) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s.length());

        int pos = 0;

        while (true) {
            int x = s.indexOf(begin, pos);
            int y = s.indexOf(end, x + begin.length());

            if (x == -1 || y == -1) {
                sb.append(s.substring(pos, s.length()));

                break;
            } else {
                sb.append(s.substring(pos, x));

                pos = y + end.length();
            }
        }

        return sb.toString();
    }

    /**
     * Substring.
     *
     * @param s
     *            the s
     * @param beginIndex
     *            the begin index
     * @param endIndex
     *            the end index
     * @return the string
     */
    public static String substring(String s, int beginIndex, int endIndex) {
        int maxLength = s.length() < endIndex ? s.length() : endIndex;
        return s.substring(beginIndex, maxLength);
    }

    /**
     * To char code.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String toCharCode(String s) {
        StringBuilder sb = new StringBuilder(s.length());

        for (int i = 0; i < s.length(); i++) {
            sb.append(s.codePointAt(i));
        }

        return sb.toString();
    }

    /**
     * To hex string.
     *
     * @param i
     *            the i
     * @return the string
     */
    public static String toHexString(int i) {
        char[] buffer = new char[8];

        int index = 8;

        do {
            buffer[--index] = _HEX_DIGITS[i & 15];

            i >>>= 4;
        } while (i != 0);

        return new String(buffer, index, 8 - index);
    }

    /**
     * To hex string.
     *
     * @param l
     *            the l
     * @return the string
     */
    public static String toHexString(long l) {
        char[] buffer = new char[16];

        int index = 16;

        do {
            buffer[--index] = _HEX_DIGITS[(int) (l & 15)];

            l >>>= 4;
        } while (l != 0);

        return new String(buffer, index, 16 - index);
    }

    /**
     * To hex string.
     *
     * @param obj
     *            the obj
     * @return the string
     */
    public static String toHexString(Object obj) {
        if (obj instanceof Integer) {
            return toHexString(((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            return toHexString(((Long) obj).longValue());
        } else {
            return String.valueOf(obj);
        }
    }

    /**
     * Trim.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String trim(String s) {
        return trim(s, null);
    }

    /**
     * Trim.
     *
     * @param s
     *            the s
     * @param c
     *            the c
     * @return the string
     */
    public static String trim(String s, char c) {
        return trim(s, new char[] { c });
    }

    /**
     * Trim.
     *
     * @param s
     *            the s
     * @param exceptions
     *            the exceptions
     * @return the string
     */
    public static String trim(String s, char[] exceptions) {
        if (s == null) {
            return null;
        }

        char[] chars = s.toCharArray();

        int len = chars.length;

        int x = 0;
        int y = chars.length;

        for (int i = 0; i < len; i++) {
            char c = chars[i];

            if (_isTrimable(c, exceptions)) {
                x = i + 1;
            } else {
                break;
            }
        }

        for (int i = len - 1; i >= 0; i--) {
            char c = chars[i];

            if (_isTrimable(c, exceptions)) {
                y = i;
            } else {
                break;
            }
        }

        if (x != 0 || y != len) {
            return s.substring(x, y);
        } else {
            return s;
        }
    }

    /**
     * Trim all.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String trimAll(String s) {
        return s.replaceAll("\\s+", " ");
    }

    /**
     * Trim double empty line.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String trimDoubleEmptyLine(String s) {
        return s.replaceAll("(?m)^\\s*$[\n\r]{2,}", "\n");
    }

    /**
     * Trim end empty line.
     *
     * @param input
     *            the input
     * @return the string
     */
    public static String trimEndEmptyLine(String input) {
        return input.replaceFirst("\\n{2,}\\z$", "");
    }

    /**
     * Trim leading.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String trimLeading(String s) {
        return trimLeading(s, null);
    }

    /**
     * Trim leading.
     *
     * @param s
     *            the s
     * @param c
     *            the c
     * @return the string
     */
    public static String trimLeading(String s, char c) {
        return trimLeading(s, new char[] { c });
    }

    /**
     * Trim leading.
     *
     * @param s
     *            the s
     * @param exceptions
     *            the exceptions
     * @return the string
     */
    public static String trimLeading(String s, char[] exceptions) {
        if (s == null) {
            return null;
        }

        char[] chars = s.toCharArray();

        int len = chars.length;

        int x = 0;
        int y = chars.length;

        for (int i = 0; i < len; i++) {
            char c = chars[i];

            if (_isTrimable(c, exceptions)) {
                x = i + 1;
            } else {
                break;
            }
        }

        if (x != 0 || y != len) {
            return s.substring(x, y);
        } else {
            return s;
        }
    }

    /**
     * Trim trailing.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String trimTrailing(String s) {
        return trimTrailing(s, null);
    }

    /**
     * Trim trailing.
     *
     * @param s
     *            the s
     * @param c
     *            the c
     * @return the string
     */
    public static String trimTrailing(String s, char c) {
        return trimTrailing(s, new char[] { c });
    }

    /**
     * Trim trailing.
     *
     * @param s
     *            the s
     * @param exceptions
     *            the exceptions
     * @return the string
     */
    public static String trimTrailing(String s, char[] exceptions) {
        if (s == null) {
            return null;
        }

        char[] chars = s.toCharArray();

        int len = chars.length;

        int x = 0;
        int y = chars.length;

        for (int i = len - 1; i >= 0; i--) {
            char c = chars[i];

            if (_isTrimable(c, exceptions)) {
                y = i;
            } else {
                break;
            }
        }

        if (x != 0 || y != len) {
            return s.substring(x, y);
        } else {
            return s;
        }
    }

    /**
     * Unquote.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String unquote(String s) {
        if (Validator.isNull(s)) {
            return s;
        }

        if (s.charAt(0) == CharPool.APOSTROPHE && s.charAt(s.length() - 1) == CharPool.APOSTROPHE) {

            return s.substring(1, s.length() - 1);
        } else if (s.charAt(0) == CharPool.QUOTE && s.charAt(s.length() - 1) == CharPool.QUOTE) {

            return s.substring(1, s.length() - 1);
        }

        return s;
    }

    /**
     * Upper case.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String upperCase(String s) {
        if (s == null) {
            return null;
        } else {
            return s.toUpperCase();
        }
    }

    /**
     * Upper case first letter.
     *
     * @param s
     *            the s
     * @return the string
     */
    public static String upperCaseFirstLetter(String s) {
        char[] chars = s.toCharArray();

        if (chars[0] >= 97 && chars[0] <= 122) {
            chars[0] = (char) (chars[0] - 32);
        }

        return new String(chars);
    }

    /**
     * Upper case first letter.
     *
     * @param s
     *            the s
     * @param defaultString
     *            the default string
     * @return the string
     */
    public static String upperCaseFirstLetter(String s, String defaultString) {
        if (Validator.isNotNull(s)) {
            return upperCaseFirstLetter(s);
        } else {
            return defaultString;
        }
    }

    /**
     * Value of.
     *
     * @param obj
     *            the obj
     * @return the string
     */
    public static String valueOf(Object obj) {
        return String.valueOf(obj);
    }

    /**
     * Wrap.
     *
     * @param text
     *            the text
     * @return the string
     */
    public static String wrap(String text) {
        return wrap(text, 80, StringPool.NEW_LINE);
    }

    /**
     * Wrap.
     *
     * @param text
     *            the text
     * @param width
     *            the width
     * @param lineSeparator
     *            the line separator
     * @return the string
     */
    public static String wrap(String text, int width, String lineSeparator) {
        if (text == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new StringReader(text));

            String s = StringPool.BLANK;

            while ((s = br.readLine()) != null) {
                if (s.length() == 0) {
                    sb.append(lineSeparator);
                } else {
                    String[] tokens = s.split(StringPool.SPACE);
                    boolean firstWord = true;
                    int curLineLength = 0;

                    for (String token : tokens) {
                        if (!firstWord) {
                            sb.append(StringPool.SPACE);
                            curLineLength++;
                        }

                        if (firstWord) {
                            sb.append(lineSeparator);
                        }

                        sb.append(token);

                        curLineLength += token.length();

                        if (curLineLength >= width) {
                            firstWord = true;
                            curLineLength = 0;
                        } else {
                            firstWord = false;
                        }
                    }
                }
            }
        } catch (IOException ioe) {
            _log.error(ioe.getMessage());
        }

        return sb.toString();
    }
    
    /**
     * Checks if is blank.
     *
     * @param str the str
     * @return true, if is blank
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * Checks if is blank.
     *
     * @param str the str
     * @return true, if is blank
     */
    public static boolean isBlank(Object str) {
        return str == null || ((String) str).trim().length() == 0;
    }

    /**
     * Checks if is empty.
     *
     * @param str the str
     * @return true, if is empty
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
