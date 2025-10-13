package com.faboslav.friendsandfoes.common.config.client.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.AbstractLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Inspired by use in Sounds mod
 *
 * @author IMB11
 * <a href="https://github.com/IMB11/Sounds/blob/main/src/main/java/dev/imb11/sounds/gui/DynamicGridWidget.java">https://github.com/IMB11/Sounds/blob/main/src/main/java/dev/imb11/sounds/gui/DynamicGridWidget.java</a>
 */
public class DynamicGridWidget extends AbstractLayout {
	private final List<GridItem> children = new ArrayList<>();
	private int padding = 0;

	public DynamicGridWidget(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void addChild(AbstractWidget widget, int cellHeight, int cellWidth) {
		this.children.add(new GridItem(cellHeight, cellWidth, widget));
	}

	public void addChild(AbstractWidget widget) {
		this.children.add(new GridItem(-1, -1, widget));
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	private boolean canFit(int gridX, int gridY, int cellWidth, int cellHeight, int optimalCells, boolean[][] grid) {
		if (gridX >= optimalCells || gridY >= optimalCells) {
			return false;
		}

		for (int x = gridX; x < gridX + cellWidth; x++) {
			for (int y = gridY; y < gridY + cellHeight; y++) {
				if (x >= grid.length || y >= grid[x].length) {
					throw new RuntimeException("Impossible to fit widget in grid!");
				}

				if (grid[x][y]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Recalculates the layout of this widget.
	 */
	public void calculateLayout() {
		int totalCells = 0;
		for (GridItem child : this.children) {
			int widgetCells = child.cellWidth() * child.cellHeight();
			totalCells += widgetCells;
		}

		int optimalCells = (int) Math.ceil(Math.sqrt(totalCells));

		int cellWidth = (this.width) / optimalCells;
		int cellHeight = (this.height) / optimalCells;

		boolean[][] grid = new boolean[optimalCells][optimalCells];

		int currentX = getX();
		int currentY = getY();

		for (GridItem child : this.children) {
			int gridX = currentX / cellWidth;
			int gridY = currentY / cellHeight;

			while (!canFit(gridX, gridY, Math.abs(child.cellWidth()), Math.abs(child.cellHeight()), optimalCells, grid)) {
				currentX += cellWidth;
				if (currentX >= this.width) {
					currentX = getX();
					currentY += cellHeight;
				}

				gridX = currentX / cellWidth;
				gridY = currentY / cellHeight;
			}

			if (gridX > optimalCells || gridY > optimalCells) {
				throw new RuntimeException("Impossible to fit widget in grid!");
			}

			if (grid[gridX][gridY]) {
				currentX += cellWidth;
				if (currentX >= this.width) {
					currentX = getX();
					currentY += cellHeight;
				}

				gridX = currentX / cellWidth;
				gridY = currentY / cellHeight;
			}

			int thisCellWidth = cellWidth;
			int thisCellHeight = cellHeight;

			boolean isMultiCell = child.cellHeight() > 1 || child.cellWidth() > 1;
			if (child.cellWidth() != -1) {
				thisCellWidth = child.cellWidth() * cellWidth;
			}

			if (child.cellHeight() != -1) {
				thisCellHeight = child.cellHeight() * cellHeight;
			}

			if (isMultiCell) {
				// Mark all cells this widget uses as taken.
				int minX = gridX;
				int minY = gridY;
				int maxX = gridX + child.cellWidth();
				int maxY = gridY + child.cellHeight();

				for (int x = minX; x < maxX; x++) {
					for (int y = minY; y < maxY; y++) {
						grid[x][y] = true;
					}
				}
			} else {
				grid[gridX][gridY] = true;
			}

			child.widget().setX(currentX);
			child.widget().setY(currentY);
			child.widget().setWidth(thisCellWidth - padding * 2);

			//? if >=1.21 {
			child.widget().setHeight(thisCellHeight - padding * 2);
			//?} else {
			/*((AbstractWidgetInvoker)child.widget()).setHeight((thisCellHeight - padding * 2));
			 *///?}

			currentX += thisCellWidth;
			if (currentX >= this.width) {
				currentX = getX();
				currentY += thisCellHeight;
			}
		}

	}

	@Override
	public void visitChildren(Consumer<LayoutElement> consumer) {
		this.children.stream().map(GridItem::widget).forEach(consumer);
	}

	public record GridItem(int cellHeight, int cellWidth, AbstractWidget widget)
	{
	}
}