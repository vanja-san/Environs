var ASM = Java.type("net.minecraftforge.coremod.api.ASMAPI");

var Opcodes = Java.type('org.objectweb.asm.Opcodes');
var FieldNode = Java.type('org.objectweb.asm.tree.FieldNode');

function initializeCoreMod()
{
    return {
        "environs_blockstate_transformer": {
            "target": {
                "type": "CLASS",
                "names": function(listofclasses) { return ["net.minecraft.block.BlockState"]; }
            },
            "transformer": function(classNode) {
                // Add a field to cache our data
                classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "environs_blockData", "Ljava/lang/Object;", null, null));
                print("[Environs Transformer]: Patched BlockState - Added block effect cache field");
                return classNode;
            }
        }
    };
}
