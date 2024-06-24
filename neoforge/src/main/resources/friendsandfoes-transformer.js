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
                var buildEndIndex = -1;

                for (var i = 0; i < instructions.size(); i++) {
                    var instruction = instructions.get(i);

                    if (instruction instanceof MethodInsnNode) {
                        if (instruction.name.equals("builder") && instruction.owner.equals("com/google/common/collect/ImmutableBiMap")) {
                            builderStartIndex = i;
                        }

                        if (builderStartIndex !== -1 && instruction.name.equals("build") && instruction.owner.equals("com/google/common/collect/ImmutableBiMap$Builder")) {
                            buildEndIndex = i;
                            break;
                        }
                    }
                }

                if (builderStartIndex !== -1 && buildEndIndex !== -1) {
                    var methodInstructions = new InsnList();
                    methodInstructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "com/faboslav/friendsandfoes/neoforge/asm/OxidizableTransformer",
                        "createMutableMap",
                        "(Lcom/google/common/collect/BiMap;)Lcom/google/common/collect/BiMap;",
                        false
                    ));

                    instructions.insert(instructions.get(buildEndIndex), methodInstructions);
                }

                return method;
            }
        }
    };
}