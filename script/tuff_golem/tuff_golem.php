<?php

$blocks = [
	'red.png',
];

$colors = [
	'red' => [
		'a22722',
		'b53129',
		'd2382e',
		'dc5040',
	],
	'black' => [
		'333065',
		'383751',
		'4f4a74',
		'584f93',
	],
	'blue' => [
		'3a4da5',
		'455abe',
		'4573c7',
		'5a84d0',
	],
	'brown' => [
		'724728',
		'8f5b35',
		'ac6e42',
		'ca8350',
	],
	'cyan' => [
		'108887',
		'129e9d',
		'14b4b4',
		'17d2d2',
	],
	'gray' => [
		'526162',
		'627173',
		'6c7b83',
		'728996',
	],
	'green' => [
		'4a6313',
		'608116',
		'77a119',
		'82b216',
	],
	'light_blue' => [
		'208dcd',
		'29a1d5',
		'41b7e7',
		'62c4ef',
	],
	'light_gray' => [
		'7d7f77',
		'93958c',
		'b1aca3',
		'c7c0b2',
	],
	'lime' => [
		'62b618',
		'7bc618',
		'9bdf39',
		'afea5a',
	],
	'magenta' => [
		'a42c9c',
		'bd3cb4',
		'cc49b9',
		'dc63ca',
	],
	'orange' => [
		'e66500',
		'ff8118',
		'ff9929',
		'fcb76b',
	],
	'pink' => [
		'de6594',
		'f689ac',
		'f8a6bd',
		'fbbdce',
	],
	'purple' => [
		'6a24a4',
		'832cb4',
		'942aca',
		'a039d5',
	],
	'white' => [
		'c7d3d3',
		'e0e5e5',
		'f8f8f8',
		'ffffff',
	],
	'yellow' => [
		'daab34',
		'ffcc4b',
		'ffda39',
		'fceba0',
	],
];

foreach ( $colors as $type => $typeColors ) {
	if($type === 'red') {
		continue;
	}

	foreach ( $blocks as $block ) {
		$blockPath = __DIR__.'/input/'.$block;
		$im = imagecreatefrompng($blockPath);
		imagealphablending($im, false);
		for ($x = imagesx($im); $x--;) {
			for ($y = imagesy($im); $y--;) {
				$rgb = imagecolorat($im, $x, $y);
				$currentColorRGB = imagecolorsforindex($im, $rgb);

				$currentColorHEX = sprintf(
					"%02x%02x%02x",
					$currentColorRGB['red'],
					$currentColorRGB['green'],
					$currentColorRGB['blue']
				);

				if(in_array($currentColorHEX, $colors['red'])) {
					$index = array_search( $currentColorHEX, $colors[ 'red' ] );
					$newColorHEX = $colors[ $type ][ $index ];
					$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );
					$colorB = imagecolorallocatealpha( $im, $newColorRGB[ 0 ], $newColorRGB[ 1 ], $newColorRGB[ 2 ], $currentColorRGB[ 'alpha' ] );

					if($colorB === false) {
						continue;
					}

					imagesetpixel( $im, $x, $y, $colorB );
				}
			}
		}

		imageAlphaBlending($im, true);
		imageSaveAlpha($im, true);

		ob_start();
		imagepng($im);
		$imageString = ob_get_clean();
		file_put_contents(
			__DIR__.'/output/'.$type.'.png',
			$imageString,
		);

	}
}
