package ion.errorsystem;

import java.util.ArrayList;

import ion.errorsystem.errors.Error;

public class ErrorSystem {

    private static boolean terminateAfterStep = false;

    private static ArrayList<Error> errors = new ArrayList<Error>();
    private static ArrayList<Warning> warnings = new ArrayList<Warning>();

    // Add error and exit right away
    public static void AddError_i(Error error) {
        errors.add(error);
        WriteAndExit();
    }

    // Add error and set the terminateAfterStep flag
    public static void AddError_s(Error error) {
        errors.add(error);
        terminateAfterStep = true;
    }

    // Add warning
    public static void AddWarning(Warning warning) {
        warnings.add(warning);
    }

    // Returns whether the terminateAfterStep flag has been set
    public static boolean ShouldTerminateAfterStep() {
        return terminateAfterStep;
    }

    // Writes all errors and warnings to the Console and exits with exitcode 1
    // afterwards
    public static void WriteAndExit() {
        if (errors.size() > 0)
            for (int i = 0; i < errors.size(); i++)
                System.err.println(errors.get(i));
        else
            for (int i = 0; i < warnings.size(); i++)
                System.out.println(warnings.get(i));

        System.exit(1);
    }

    // Writes the given message to the Console and exits
    public static void InternalError(String msg) {
        System.err.println("INTERNAL ERROR: " + msg);
        System.exit(1);
    }

}
