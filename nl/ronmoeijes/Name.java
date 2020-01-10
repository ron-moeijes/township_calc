package nl.ronmoeijes;

class Name {
    static String createName(String id){
        return camelCase(id);
    }

    private static String camelCase(String str) {
        str = str.replaceAll("_", " ");
        str = letterToUpperCase(str, 0);
        if (str.indexOf(" ") > 0) {
            str = letterToUpperCase(str, str.indexOf(" ")+1);
        }
        return str;
    }

    private static String letterToUpperCase(String str, int i) {
        int j = i+1;
        String c = str.substring(i, j).toUpperCase();
        if (i != 0) {
            return str.substring(0, i) + c + str.substring(j);
        } else { return c + str.substring(j); }
    }
}
