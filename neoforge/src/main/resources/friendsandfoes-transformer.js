function initializeCoreMod() {
    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
    var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
    var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

    return {
        'oxidation_level_increases_transformer': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.block.Oxidizable',
                'methodName': 'method_34740',
                'methodDesc': '()Lcom/google/common/collect/BiMap;'
            },
            'transformer': function(method) {
                var instructions = method.instructions;
                var builderStartIndex = -1;

                for (var i = 0; i < instructions.size(); i++) {
                    var instruction = instructions.get(i);

                    if (instruction instanceof MethodInsnNode && instruction.name.equals("builder")) {
                        builderStartIndex = i;
                    }

                    if (instruction instanceof MethodInsnNode && instruction.name.equals("build")) {
                        builderEndIndex = i;
                        break;
                    }
                }

                if (builderStartIndex !== -1 && builderEndIndex !== -1) {
                    var methodInstructions = new InsnList();
                    methodInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/faboslav/friendsandfoes/neoforge/asm/OxidizableTransformer", "injectCustomOxidizableBlocks", "(Lcom/google/common/collect/ImmutableBiMap$Builder;)Lcom/google/common/collect/ImmutableBiMap$Builder;", false));
                    instructions.insertBefore(instructions.get(builderEndIndex), methodInstructions);
                }

                return method;
            }
        }
    };
}