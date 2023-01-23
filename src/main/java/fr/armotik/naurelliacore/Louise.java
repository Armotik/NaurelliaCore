package fr.armotik.naurelliacore;

public class Louise {

    private Louise() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * @return louise name
     */
    public static String getName() {
        return "§7[§aLouise§7] : ";
    }

    /**
     * @return chat filter message
     */
    public static String permissionMissing() {
        return getName() + "§c You don't have the permission !";
    }

    /**
     * @return wrong command message
     */
    public static String wrongCommand() {
        return getName() + "§cWrong command. Please respect the schema.";
    }

    /**
     * @return player not found message
     */
    public static String playerNotFound() {
        return getName() + "§cUnknown player";
    }

    public static String notOnline()
}
