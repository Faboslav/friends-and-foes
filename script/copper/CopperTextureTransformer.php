<?php

declare(strict_types=1);

final class CopperTextureTransformer
{
	public $pixelColorMap = [];
	public $colorMap = [];

	/**
	 * @param array<string> $additionalInputImages
	 */
	public function __construct(
		private string $defaultInputImage,
		private array $additionalInputImages,
		private string $inputImage,
	) {
		$this->parsePixelColors($this->defaultInputImage);

		foreach ($this->additionalInputImages as $additionalInputImage) {
			$this->parsePixelColors($additionalInputImage);
		}

		$this->createColorMap();
		$this->createOutputImages();
	}

	public function parsePixelColors(
		string $inputImageName
	): void {
		$blockPath = __DIR__ . '/input/' . $inputImageName . '.png';
		$image = imagecreatefrompng($blockPath);
		imagealphablending($image, false);
		for ($x = imagesx($image); $x--;) {
			for ($y = imagesy($image); $y--;) {
				$rgb = imagecolorat($image, $x, $y);
				$currentColorRGB = imagecolorsforindex($image, $rgb);
				$currentColorHEX = sprintf(
					"%02x%02x%02x",
					$currentColorRGB['red'],
					$currentColorRGB['green'],
					$currentColorRGB['blue']
				);

				$this->pixelColorMap[$inputImageName][$x][$y] = $currentColorHEX;
			}
		}
	}

	public function createColorMap(): void
	{
		foreach($this->pixelColorMap[$this->defaultInputImage] as $x) {
			foreach ($x as $y => $colorHex) {
				foreach($this->additionalInputImages as $additionalInputImage) {
					if(array_key_exists($additionalInputImage, $this->colorMap) === false) {
						$this->colorMap[$additionalInputImage] = [];
					}

					if(array_key_exists($colorHex, $this->colorMap[$additionalInputImage]) === false) {
						$this->colorMap[$additionalInputImage][$colorHex] = [];
					}
				}
			}
		}

		foreach($this->additionalInputImages as $additionalInputImage) {
			foreach($this->pixelColorMap[$additionalInputImage] as $xPixel => $mappedY) {
				foreach ($mappedY as $yPixel => $colorHex) {
					$colorKey = $this->pixelColorMap[$this->defaultInputImage][$xPixel][$yPixel];

					$this->colorMap[$additionalInputImage][$colorKey][] = $colorHex;

				}
			}
		}
	}

	public function createOutputImages(): void
	{
		foreach($this->additionalInputImages as $additionalInputImage) {
			$blockPath = __DIR__.'/input/'.$this->inputImage.'.png';
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

					if(isset($this->colorMap[$additionalInputImage][$currentColorHEX]) === false) {
						continue;
					}

					$possibleNewColors = $this->colorMap[$additionalInputImage][$currentColorHEX];
					$randomColorIndex = rand(0, sizeof($possibleNewColors) - 1);
					$newColorHEX = $possibleNewColors[$randomColorIndex];
					$newColorRGB = list( $r, $g, $b ) = sscanf( $newColorHEX, "%02x%02x%02x" );
					$newColor = imagecolorallocatealpha( $im, $newColorRGB[ 0 ], $newColorRGB[ 1 ], $newColorRGB[ 2 ], $currentColorRGB[ 'alpha' ] );
					imagesetpixel($im, $x, $y, $newColor);
				}
			}

			imageAlphaBlending($im, true);
			imageSaveAlpha($im, true);

			ob_start();
			imagepng($im);
			$imageString = ob_get_clean();
			file_put_contents(
				__DIR__.'/output/'.$additionalInputImage . '.png',
				$imageString,
			);
		}
	}
}

$textureTransformer = new CopperTextureTransformer(
	'copper',
	[
    	'exposed_copper',
    	'oxidized_copper',
    	'weathered_copper',
    ],
    'lightning_rod',
);
