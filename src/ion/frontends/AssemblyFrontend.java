package ion.frontends;

import ion.errorsystem.ErrorSystem;
import ion.errorsystem.errors.frontend.MissingMainFunctionError;
import ion.parser.ParserResult;
import ion.utils.Function;
import ion.utils.Variable;

public class AssemblyFrontend extends Frontend<String> {

    public AssemblyFrontend(ParserResult parserResult) {
        super(parserResult);
    }

    @Override
    public String generate() {
        for(Function function : functions.values()) function.getBody().typeCheck();

        if(!functions.containsKey("main"))
            ErrorSystem.AddError_i(new MissingMainFunctionError()); // TODO: maybe change this to ErrorSystem.AddError_s()

        String asm = "";
        asm += "BITS 64\n";

        asm += "segment .bss\n";
        for(Variable variable : variables.values()) asm += "    var_" + variable.getId() + ": resq 1\n";

        asm += "segment .text\n";

        asm += """
                put_u:
                    ; preparation
                    mov rbx, 10
                    mov r8, 0
                .L1:
                    ; make chars
                    xor rdx, rdx
                    div rbx
                    add rdx, 48
                    push rdx
                    inc r8
                    cmp rax, 0
                    jne .L1
                .L2:
                    ; print chars
                    mov rax, 1
                    mov rdi, 1
                    mov rsi, rsp
                    mov rdx, 1
                    syscall
                    pop rdx
                    dec r8
                    cmp r8, 0
                    jne .L2
                    ; print newline
                    push 10
                    mov rax, 1
                    mov rdi, 1
                    mov rsi, rsp
                    mov rdx, 1
                    syscall
                    pop rdx
                    ; return
                    ret
                """;

        for(Function function : functions.values()) asm += function.generateAssembly();

        asm += "global _start\n";
        asm += "_start:\n";

        // TODO: prepare the program arguments to pass them on to the main function
        asm += "    call func_" + functions.get("main").getId() + "\n";

        asm += "exit:\n";
        asm += "    mov rax, 60\n";
        asm += "    mov rdi, 0\n";
        asm += "    syscall\n";

        return asm;
    }

}
