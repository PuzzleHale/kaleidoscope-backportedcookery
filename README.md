# Kaleidoscope: Backported Cookery

森罗物语附属模组。把《森罗物语：厨房——重织》（Kaleidoscope Cookery Refabricated，MC 26.3 / 1.6.x）中的 **铜菜刀**、**稻草床（Straw Bed）**、**12 种食物**、**极品蛋炒饭** 与 **层孔菇瓦罐汤** 回移植到 **NeoForge 1.21.1**。

- 模组 ID：`kaleidoscope_backportedcookery`
- 必需前置：
  - **森罗物语：厨房**（`kaleidoscope_cookery` ≥ 1.6.0）
  - **Vanilla Backport**（`vanillabackport` ≥ 1.2.0）——提供 1.21.1 缺失的 `minecraft:shelf_mushroom` 与 `minecraft:straw_bed`
  - **Platform**（`platform` ≥ 1.4，Vanilla Backport 自身的必需库）
- 建议 NeoForge **21.1.253 或更高**：Vanilla Backport 1.2.x 同时包含游戏总线与模组总线监听器，FML 4.0.45（NeoForge 21.1.253 起）才会自动分流；更旧的 NeoForge 会拒绝加载 Vanilla Backport。
- 仅包含这些移植内容，不新增任何其它无关物品／方块。

## 内容 ID

沿用「森罗物语：厨房」的命名空间与命名风格（与官方附属模组 `kaleidoscope_donkey_meat_reborn` 的做法一致：模组 ID 是自己的，物品 ID 属于主模组命名空间）：

| 内容 | 物品 / 方块 ID | 中文 | English |
| --- | --- | --- | --- |
| 铜菜刀 | `kaleidoscope_cookery:copper_kitchen_knife` | 铜菜刀 | Copper Kitchen Knife |
| 稻草床 | `kaleidoscope_cookery:straw_bed` | 稻草床 | Straw Bed |
| 层孔菇瓦罐汤 | `kaleidoscope_cookery:shelf_mushroom_pot_soup` | 层孔菇瓦罐汤 | Shelf Mushroom Pot Soup |
| 极品蛋炒饭 | `kaleidoscope_cookery:delicious_egg_fried_rice` | 极品蛋炒饭 | Delicious Egg Fried Rice |

## 已移植的食物

| 内容 | ID | 中文 | 食物值（饥饿／饱和度） | 效果 |
| --- | --- | --- | --- | --- |
| 生驴肉 | `kaleidoscope_cookery:raw_donkey_meat` | 生驴肉 | 2／0.3 | — |
| 熟驴肉 | `kaleidoscope_cookery:cooked_donkey_meat` | 熟驴肉 | 6／0.8 | — |
| 爆炒牛杂 | `kaleidoscope_cookery:stir_fried_beef_offal` | 爆炒牛杂 | 7／0.3 | 胀气 30s、温暖 60s |
| 牛杂盖浇饭 | `kaleidoscope_cookery:stir_fried_beef_offal_rice_bowl` | 牛杂盖浇饭 | 14／0.4 | 胀气 30s、温暖 60s |
| 农家杂蔬 | `kaleidoscope_cookery:country_style_mixed_vegetables` | 农家杂蔬 | 4／0.4 | 保鲜 220s、活力 45s |
| 红烧鱼盖浇饭 | `kaleidoscope_cookery:braised_fish_rice_bowl` | 红烧鱼盖浇饭 | 14／0.4 | 水下呼吸 180s、饱腹护盾 80s |
| 辣子鸡盖浇饭 | `kaleidoscope_cookery:spicy_chicken_rice_bowl` | 辣子鸡盖浇饭 | 14／0.4 | 抗火 80s、抗性提升 100s |
| 诡异炒饭盖饭 | `kaleidoscope_cookery:suspicious_stir_fry_rice_bowl` | 诡异炒饭盖饭 | 8／0.4 | 速度／急迫／幸运／疲劳／反胃／跳跃提升各 60s（15%） |
| 极品蛋炒饭 | `kaleidoscope_cookery:delicious_egg_fried_rice` | 极品蛋炒饭 | 12／0.4 | 温暖 45s |
| 番茄牛腩汤 | `kaleidoscope_cookery:tomato_beef_brisket_soup` | 番茄牛腩汤 | 5／0.4 | 饱腹护盾 180s |
| 驴肉汤 | `kaleidoscope_cookery:donkey_soup` | 驴肉汤 | 6／0.667 | 温暖 8min |
| 红烧鱼 | `kaleidoscope_cookery:braised_fish` | 红烧鱼 | 拼盘每口 2／0.8；物品 8／0.8 | 饱腹护盾 80s、水下呼吸 180s |
| 日式烤串 | `kaleidoscope_cookery:yakitori` | 日式烤串 | 拼盘每口 2／0.6；物品 10／0.6 | 饱腹护盾 80s、温暖 40s |
| 水果拼盘 | `kaleidoscope_cookery:fruit_platter` | 水果拼盘 | 拼盘每口 3／0.667；物品 13／0.615 | — |
| 层孔菇瓦罐汤 | `kaleidoscope_cookery:shelf_mushroom_pot_soup` | 层孔菇瓦罐汤 | 方块每口 4／0.667；物品 13／0.667 | 温暖 5min（100%） |

> 「驴肉」在原模组中是 **生驴肉 + 熟驴肉** 两个 ID，故一并移植；生驴肉也是驴肉汤与熟驴肉的原料。

### 层孔菇瓦罐汤
- 与 26.3 完全一致：**两份**、方块碰撞箱为「花盆 + 罐体」组合形状、汤面持续冒泡（`POT_SOUP_ANIMATE_TICK`）、吃完掉落 `minecraft:flower_pot`。
- 物品形态使用主模组的 `BowlFoodBlockItem`（本模组用其子类 `BackportedPotSoupItem` 把容器物品修正为花盆）：堆叠 16、随烹饪品质缩放、自动显示 `.maxim` tooltip、食用后返回花盆。
- 配方与 26.3 相同：汤锅 `stockpot`（2 个层孔菇）与弹性汤锅 `flex_stockpot`（1 个层孔菇），容器均为花盆。
- 层孔菇使用 **Vanilla Backport 的 `minecraft:shelf_mushroom`**（1.21.1 原版没有该物品）。
- 与 26.3 一致加入 `#kaleidoscope_cookery:meals` 与 `#kaleidoscope_cookery:cookery_mod_items` 标签，供主模组的餐食判定与兼容逻辑使用。

### 极品蛋炒饭
- 26.3 中为 `BowlFoodOnlyItem`：12 饥饿／0.4 饱和度、温暖 900 tick（45 秒）、始终可食用、吃完返碗。
- 配方与 26.3 相同：炒锅 `pot`，**2 个蛋 + 2 个生菜 + 1 个熟米饭 + 1 个胡萝卜**，容器为碗。
- **蛋类联动**：配方使用自定义标签 `#kaleidoscope_cookery:delicious_egg_fried_rice_eggs`，包含 `#c:eggs`、`minecraft:turtle_egg`、`minecraft:brown_egg`、`minecraft:blue_egg`，以及可选的 `minecraft:green_egg`（存在时自动生效）。Vanilla Backport 的棕色蛋与蓝色蛋（26.3 的另一种鸡蛋；VB 的译名为「蓝色鸡蛋」）均可直接使用，海龟蛋也可作为替代品。

### 食物实现细节
- 9 种碗装菜（含极品蛋炒饭）使用主模组的 `BowlFoodOnlyItem`：食用后返回碗、随烹饪品质缩放、自动显示 `.maxim` tooltip。
- 红烧鱼／日式烤串／水果拼盘是 **可放置拼盘方块**：`FoodBiteBlock`，默认 4 份，每右键吃一份，吃完或空手右键收走时按 26.3 战利品表掉落（红烧鱼掉鱼骨／骨粉，水果拼盘掉碗，烤串不掉落）；物品形态也可直接食用（满额营养）。
- 生驴肉沿用 26.3 的获取方式：主模组已存在 `neoforge:add_table` 驴掉落修饰器，本模组补上缺失的 `kaleidoscope_cookery:donkey` 战利品表（主手持有 `#kaleidoscope_cookery:kitchen_knife` 时掉落 1–2 个，受抢夺影响）。
- 配方：爆炒牛杂／牛杂盖浇饭／农家杂蔬／红烧鱼（鳕鱼、鲑鱼、鳕鱼+鲑鱼三种）／红烧鱼盖浇饭（两种）／辣子鸡盖浇饭（含烈焰粉变体）／日式烤串／熟驴肉使用炒锅 `kaleidoscope_cookery:pot`；番茄牛腩汤／驴肉汤／层孔菇瓦罐汤使用汤锅；水果拼盘为无序合成。
- **配方精确度修复**：红烧鱼补回 26.3 的「1 鳕鱼 + 1 鲑鱼」配方（原先只有 6 鳕鱼 / 6 鲑鱼两种）；红烧鱼盖浇饭与牛杂盖浇饭等盖饭的容器改用 `#c:cooked_rice` 标签（与 26.3 的 `carrier` 完全一致，可接受其它模组的熟米饭）；爆炒牛杂写死 7 项配料（3 生牛杂 + 2 辣椒 + 2 生菜）、`time: 200`、`stir_fry_count: 3`，与 26.3 逐字段一致。
- 诡异炒饭盖饭在原版 26.3 通过厨师交易／战利品箱获得，没有工作台配方；为在纯 1.21.1 生存中可获取，本模组额外提供 `诡异炒饭 + 米饭 → 诡异炒饭盖饭` 的无序配方（README 明确标注为补充配方）。

## 功能对照

### 铜菜刀
- 直接实例化主模组的 `KitchenKnifeItem`，因此继承 26.3 版本的 tooltip、`canPerformAction`（禁止横扫）与全部菜刀逻辑。
- 复刻 26.3 铜工具材料：耐久 190、挖掘速度 5.0、附魔能力 13、铜锭修复；武器修饰值 3.5（玩家基础 1 → 4.5 伤害）、攻击速度 -2.4，与 26.3 `sword(ToolMaterial.COPPER, 2.5F, -2.4F)` 等价。
- 通过标签合并加入 `#kaleidoscope_cookery:kitchen_knife`、`#c:tools/knives`、`#minecraft:swords`、`#kaleidoscope_cookery:cookery_mod_items`。主模组的砧板切割、猪／猪灵／疣猪兽掉落油脂、附魔（`#minecraft:enchantable/sword`）等全部按标签判定，因此自动生效。
- 注册发射器切菜行为（复用主模组 `ChoppingBoardDispenseBehavior`），对应 26.3 更新日志里「修复铜菜刀无法在发射器中工作」。
- 配方：`## / #S`（`#` = `#c:ingots/copper`，`S` = 木棍），产出 1 把。

### 稻草床
- 两格方块（`facing` / `part=head|foot` / `occupied`），放置时自动生成床头，破坏时联动，只有床头掉落物品。
- 可睡过夜晚，但 **不重置出生点**；一次性，睡醒或点击「起床」后消失且不掉落。
- 在床不可用的维度（下界／末地）使用时直接销毁、不爆炸、不掉落。
- **不加入 `#minecraft:beds`**（与 26.3 原版一致），所以村民不会把它当作床。
- 最大堆叠 16、可用锄更快破坏（`#minecraft:mineable/hoe`）、可堆肥。
- **碰撞箱**：直接借用 Vanilla Backport 的 `minecraft:straw_bed` 方块状态形状（4/16 高的床板 + 5/16 高的枕头），不再是原版床的 9/16 高碰撞箱；即使以后 Vanilla Backport 调整形状也会自动跟随。
- **手持物品模型**：合并床头 + 床尾生成完整的 32 单位长 3D 模型（`block/straw_bed_full`），再配合居中 display 变换，不再是只显示一半或 16×16 平面图标。
- **无弹跳**：重写 `updateEntityAfterFallOn`，取消 `BedBlock#bounceUp`；玩家落到床上时只清除垂直速度，不再被弹起。`fallOn` 保留原版床的半摔落伤害。
- **渲染**：床头／床尾以及完整物品模型显式声明 `render_type: minecraft:cutout`，修复床单褶皱与枕头边缘透明像素被渲染成黑色／不透明的问题。
- 配方：`kaleidoscope_cookery:straw_block` ×3 → 稻草床 ×4（与「重织」26.3 版本一致；原版 26.3 用的是 `minecraft:hay_block`，本模组按「重织」实现）。
- 方块模型、方块状态与纹理取自「重织」26.3 资源（见 `LICENSE-ASSETS.txt`）。

### 创造模式物品栏位置

按 26.3 `ModCreativeTabs` 的实际顺序，用 `insertBefore` / `insertAfter` 精确插入，而不是简单追加：

- **主标签页**（`cookery_main`）：铜菜刀插到金菜刀之前；稻草床放在红灯笼之后（26.3 主标签页的最后一位）。
- **食物标签页**（`cookery_food`）：
  - 肉类：生驴肉 / 熟驴肉插在熟猪五花之后、生小块肉之前。
  - 小碗菜和盖饭：极品蛋炒饭插在蛋炒饭之后、番茄炒蛋之前；爆炒牛杂、牛杂盖浇饭插在番茄炒蛋盖饭之后；农家杂蔬插在糖醋里脊盖饭之后；红烧鱼盖浇饭、辣子鸡盖浇饭、诡异炒饭盖饭依次插在鱼香肉丝盖饭之后。
  - 汤类：番茄牛腩汤插在野蘑菇兔肉汤之后；驴肉汤插在香菇炖鸡之后。
  - 硬菜：红烧鱼插在紫颂果煎蛋之后；日式烤串插在辣子鸡之后；水果拼盘插在炸春卷之后；层孔菇瓦罐汤插在佛跳墙之后、金沙拉之前。
- 若使用缺少某个锚点的旧版前置，插入会自动回退为追加，不会让创造菜单崩溃。

## 构建与运行

需要 **JDK 21** 与网络（Gradle 会下载 Minecraft / NeoForge 依赖）。

```bash
cd kaleidoscope-backportedcookery
./gradlew build          # Windows: gradlew.bat build
./gradlew runClient       # 开发客户端（含前置模组）
```

产物：`build/libs/kaleidoscope_backportedcookery-1.3.0.jar`。

运行时必须同时安装：

- `kaleidoscopecookery-1.6.0-neoforge+mc1.21.1.jar`（或兼容的 1.21.1 版本）
- `VanillaBackport-neoforge-1.21.1-1.2.2.jar`（或 ≥ 1.2.0 的 1.21.1 NeoForge 版本）
- `Platform-neoforge-1.21.1-1.4.2.jar`（Vanilla Backport 的必需库）

> **NeoForge 版本**：请使用 **21.1.253 或更高**。Vanilla Backport 1.2.x 的 `ModEvents` 同时包含游戏总线与模组总线监听器，FML 4.0.45（NeoForge 21.1.253 起）会自动分流；旧版 NeoForge 会直接拒绝加载 Vanilla Backport。

编译期依赖通过 Modrinth Maven 拉取（Vanilla Backport 与 Platform 仅作为开发运行期依赖）：

```groovy
implementation "maven.modrinth:v17FatAc:g8w0b2ly"          // Kaleidoscope Cookery 1.6.0
runtimeOnly "maven.modrinth:vanillabackport:mGS7BxD4"      // Vanilla Backport 1.2.2 NeoForge
runtimeOnly "maven.modrinth:platform:rh4p4ixM"             // Platform 1.4.2 NeoForge
```

> Modrinth Maven 的 `vanillabackport:1.2.2` / `platform:1.4.2` 会解析到 Forge 构建；必须使用上表中的**版本 ID**（`mGS7BxD4` / `rh4p4ixM`）才是 NeoForge 构建。

若无法访问 Modrinth Maven，可把前置模组 jar 放进 `libs/`，并把 `build.gradle` 中对应行替换为 `implementation files("libs/...")` / `runtimeOnly files("libs/...")`。

### 已实际验证

- `./gradlew build` → `BUILD SUCCESSFUL`（NeoForge 21.1.266 / JDK 21）。
- `./gradlew runServer` → 服务端 `Done (12.558s)`，模组列表同时包含 `Kaleidoscope Cookery 1.6.0`、`Kaleidoscope: Backported Cookery 1.3.0`、`VanillaBackport 1.2.2`、`Platform 1.4.2`；共加载 **2287** 条配方、**1912 / 1943** 条进度，配置 `kaleidoscope_backportedcookery-common.toml` 正常生成。
- 逐项确认注册与配方：`kaleidoscope_cookery:shelf_mushroom_pot_soup`、`kaleidoscope_cookery:straw_bed`、`kaleidoscope_cookery:delicious_egg_fried_rice` 已注册；`minecraft:shelf_mushroom` 与 `minecraft:straw_bed` 由 Vanilla Backport 提供；`braised_fish` 的鳕鱼／鲑鱼／鳕鱼+鲑鱼三条配方、`braised_fish_rice_bowl` 两条、`stir_fried_beef_offal` 与其盖饭、瓦罐汤的 `stockpot` 与 `flex_stockpot`、极品蛋炒饭的 `pot` 配方均已加载。
- 极品蛋炒饭蛋类标签实际解析为：`minecraft:egg`、`minecraft:blue_egg`、`minecraft:brown_egg`、`kaleidoscope_cookery:fried_egg`、`minecraft:turtle_egg`；`minecraft:green_egg` 作为可选条目，当前 Vanilla Backport 未提供，因此不会报错。
- 直接构建两个创造标签页并打印内容：主标签页 **86** 项（`sickle → copper_kitchen_knife → gold_kitchen_knife ... red_lantern → straw_bed`）；食物标签页 **140** 项（`cooked_pork_belly → raw_donkey_meat → cooked_donkey_meat`、`cooked_rice → egg_fried_rice → delicious_egg_fried_rice → scramble_egg_with_tomatoes`、`buddha_jumps_over_the_wall → shelf_mushroom_pot_soup → golden_salad` 等位置均与 26.3 一致）。
- 说明：若在旧版 NeoForge（如 21.1.176）上运行，Vanilla Backport 会因其自身事件总线注解问题加载失败；请使用 NeoForge 21.1.253+。

## 项目结构

```
src/main/java/com/kaleidoscope/backportedcookery/
├── KaleidoscopeBackportedCookery.java   @Mod 入口、注册配置 / 事件总线
├── BackportedConfig.java                ModConfigSpec 配置项 + 安全读取
├── registry/
│   ├── ModBlocks.java                   方块注册（稻草床 + 3 个拼盘 + 瓦罐汤）
│   ├── ModItems.java                    物品注册（菜刀 / 稻草床 / 14 个食物 ID）
│   ├── BackportedFoods.java             26.3 食物值与状态效果
│   └── CopperTier.java                  1.21.1 缺失的铜工具材料
├── block/
│   ├── StrawBedBlock.java               稻草床行为 + Vanilla Backport 碰撞箱
│   ├── BackportedFoodBiteBlock.java     可配置的拼盘食用
│   └── BackportedPotSoupBlock.java      瓦罐汤方块（汤锅形状 + 冒泡粒子）
├── item/
│   ├── BackportedFoodBlockItem.java    拼盘物品的 tooltip
│   └── BackportedPotSoupItem.java      瓦罐汤物品（花盆容器）
└── event/
    ├── ModEvents.java                    通用设置 / 创造标签插入 / 睡醒 / 出生点 / Tick
    └── StrawBedSleepTracker.java         一次性睡卧状态跟踪
src/main/resources/
├── META-INF/neoforge.mods.toml           模组与依赖声明（neoforge / minecraft / kaleidoscope_cookery / vanillabackport）
├── assets/kaleidoscope_cookery/           物品模型 + 方块状态（须与物品 ID 同命名空间）
├── assets/kaleidoscope_backportedcookery/ 纹理、方块模型、中英文语言文件
└── data/
    ├── kaleidoscope_cookery/              配方、配方进度、战利品表、标签合并
    ├── c/tags/item/                      #c:tools/knives、#c:ingots/copper
    └── minecraft/tags/                    #minecraft:swords、#minecraft:mineable/hoe
```

## 配置项

配置文件：`config/kaleidoscope_backportedcookery-common.toml`（首次启动生成）。

| 分组 | 键 | 默认值 | 说明 |
| --- | --- | --- | --- |
| `copper_knife` | `durability` | 190 | 耐久（注册期读取，重启生效） |
| | `miningSpeed` | 5.0 | 挖掘速度（注册期读取） |
| | `attackDamageBonus` | 0.5 | Tier 伤害加成，0.5 = 26.3 铜菜刀的 4.5 伤害 |
| | `enchantability` | 13 | 附魔能力（注册期读取） |
| | `enableDispenserChopping` | true | 发射器切菜 |
| `straw_bed` | `maxStackSize` | 16 | 最大堆叠（注册期读取，重启生效） |
| | `enableSleep` | true | 关闭后变为纯装饰 |
| | `singleUse` | true | 睡一次即消失 |
| | `setSpawnPoint` | false | 是否重置出生点（26.3 为 false） |
| | `destroyInWrongDimension` | true | 下界／末地使用即销毁 |
| | `compostChance` | 0.65 | 堆肥成功率，0 = 不注册 |
| `foods` | `enableStatusEffects` | true | 是否附加 26.3 的食物状态效果（注册期读取，重启生效） |
| | `foodBlockServings` | 4 | 拼盘份数（注册期读取，重启生效；瓦罐汤固定 2 份） |
| | `enableFoodBlockBites` | true | 是否允许右键食用已放置的拼盘／瓦罐汤 |

注册期读取的选项在配置文件尚未加载时会回退到上表默认值（26.3 原值）；运行时选项即时生效。

## 1.21.1 适配说明

以下是为把 26.3 内容落到 1.21.1 而做的必要等价替换：

1. **无 `Tiers.COPPER` / `ToolMaterial.COPPER`**：自定义 `CopperTier` 实现 `Tier`；`#incorrect_for_copper_tool` 用 `#minecraft:incorrect_for_stone_tool` 代替（菜刀本身只允许 `SWORD_DIG`，无实际影响）。
2. **无 `SoundType.STRAW_BED` / `PushReaction.POPPED`**：用 `SoundType.GRASS` / `PushReaction.DESTROY`。
3. **1.21.1 无组合物品模型**：稻草床物品改用「床头方块模型 + display 变换」的 3D 物品模型，不再使用平面图标；拼盘与瓦罐汤仍使用标准平面物品图标。
4. **`ComposterBlock#add` 在 1.21.1 是私有方法**：通过反射注册堆肥，失败时仅丢失堆肥属性并打印警告。
5. **`BedBlock` 在 1.21.1 用方块实体渲染**：重写 `getRenderShape` 为 `MODEL`、`newBlockEntity` 返回 `null`，以启用 JSON 方块模型。
6. **稻草床碰撞箱**：1.21.1 原版床是 9/16 高；26.3 稻草床只有 4/16（+枕头 5/16）。本模组把 `getShape` 委托给 Vanilla Backport 的 `minecraft:straw_bed` 方块状态，保证形状与它完全一致。
7. **26.3 方块模型的多轴旋转**：26.3 的 `shelf_mushroom_pot_soup` 模型使用 Blockbench 的 `x/y/z` 欧拉角，1.21.1 只支持单轴 `axis/angle`。生成资源时把 `(180,0,180)` 精确转换为 `axis:y, angle:180`，少数斜置装饰件烘焙为旋转后的包围盒，保证 1.21.1 能正常解析模型。
8. **出生点**：用 `PlayerSetSpawnEvent` 取消；一次性销毁用 `PlayerWakeUpEvent` + 服务端 Tick 兜底。
9. **数据格式**：配方 `result.id`、标签 `data/<ns>/tags/item/...`、战利品表 `minecraft:block_state_property`、进度 `recipe_unlocked.recipe`（单数）均为 1.21.1 语法。

## 授权

- 本模组新增 Java 代码：BSD 3-Clause。
- 纹理、模型等资源文件衍生自《森罗物语：厨房》/《森罗物语：厨房——重织》，原作者「药水棒冰」等，遵循 **CC BY-NC-SA 4.0**（非商业、署名、相同方式共享）。详见 `LICENSE-ASSETS.txt`。
- 前置模组的源码（BSD 3-Clause）与二进制通过 Modrinth Maven 使用，请遵守其许可证。
- Vanilla Backport 与 Platform 仅作为运行时前置，不包含其代码或资源。
