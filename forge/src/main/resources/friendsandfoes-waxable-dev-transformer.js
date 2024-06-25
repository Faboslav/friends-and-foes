function initializeCoreMod() {
    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
    var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
    var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
    var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    return {
        'oxidation_level_increases_transformer': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.item.HoneycombItem'
            },
            'transformer': function(classNode) {
                var methods = classNode.methods;
                for (var i = 0; i < methods.size(); i++) {
                    var method = methods.get(i);
                    if (method.name.equals("method_34723") && method.desc.equals('()Lcom/google/common/collect/BiMap;')) {
                        transformMethod(method);
                    }
                }
                return classNode;
            }
        }
    };

    function transformMethod(method) {
        var instructions = method.instructions;
        var builderStartIndex = -1;
        var buildEndIndex = -1;

        // Find the call to ImmutableBiMap.builder()
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
                "com/faboslav/friendsandfoes/forge/asm/WaxableTransformer",
                "createMutableMap",
                "(Lcom/google/common/collect/BiMap;)Lcom/google/common/collect/BiMap;",
                false
            ));

            instructions.insert(instructions.get(buildEndIndex), methodInstructions);
        }

        return method;
    }
}